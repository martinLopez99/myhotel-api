package com.myhotel.backendtest.service.impl;

import com.myhotel.backendtest.config.CacheConfig;
import com.myhotel.backendtest.dao.VehicleDAO;
import com.myhotel.backendtest.dao.criteria.VehicleSearchCriteria;
import com.myhotel.backendtest.entity.Vehicle;
import com.myhotel.backendtest.exception.*;
import com.myhotel.backendtest.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de vehículos con caching en memoria.
 * Lecturas por id/plate se cachean; escrituras invalidan caches de forma programática.
 * Aplica try/catch → ServiceException.
 *
 * @author Martin Lopez
 */
@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {

    private static final Logger log = LoggerFactory.getLogger(VehicleServiceImpl.class);

    @Autowired
    private VehicleDAO vehicleDAO;
    
    @Autowired
    private CacheManager cacheManager;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheConfig.VEHICLE_BY_ID,
               key = "#id")
    public Optional<Vehicle> getById(Long id) {
        try {
            log.debug("Cache MISS – cargando Vehicle id={} desde DB (incluyendo eliminados)", id);
            Optional<Vehicle> vehicle = vehicleDAO.findByIdIncludingDeleted(id);
            if (vehicle.isEmpty()) {
                throw new ServiceException("No se encontró Vehicle con id=" + id,
                        ErrorCode.VEHICLE_NOT_FOUND);
            }
            return vehicle;
        } catch (ServiceException e) {
            throw e;
        } catch (DAOException e) {
            throw new ServiceException("Error accediendo a datos al buscar Vehicle id=" + id,
                    ErrorCode.UNEXPECTED_ERROR, e);
        } catch (Exception e) {
            throw new ServiceException("Error inesperado al buscar Vehicle id=" + id,
                    ErrorCode.UNEXPECTED_ERROR, e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheConfig.VEHICLE_BY_PLATE,
               key = "#plate != null ? #plate.trim().toUpperCase() : null")
    public Optional<Vehicle> getByPlate(String plate) {
        try {
            log.debug("Cache MISS – cargando Vehicle plate='{}' desde DB", plate);
            return vehicleDAO.findByPlate(plate);
        } catch (ServiceException e) {
            throw e;
        } catch (DAOException e) {
            throw new ServiceException("Error accediendo a datos al buscar Vehicle plate=" + plate,
                    ErrorCode.UNEXPECTED_ERROR, e);
        } catch (Exception e) {
            throw new ServiceException("Error inesperado al buscar Vehicle plate=" + plate,
                    ErrorCode.UNEXPECTED_ERROR, e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByPlate(String plate) {
        try {
            return vehicleDAO.existsByPlate(plate);
        } catch (ServiceException e) {
            throw e;
        } catch (DAOException e) {
            throw new ServiceException("Error accediendo a datos al verificar plate=" + plate,
                    ErrorCode.UNEXPECTED_ERROR, e);
        } catch (Exception e) {
            throw new ServiceException("Error inesperado al verificar plate=" + plate,
                    ErrorCode.UNEXPECTED_ERROR, e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vehicle> findAll(int offset, int limit) {
        try {
            return vehicleDAO.findAll(offset, limit);
        } catch (ServiceException e) {
            throw e;
        } catch (DAOException e) {
            throw new ServiceException("Error accediendo a datos al listar Vehicle",
                    ErrorCode.UNEXPECTED_ERROR, e);
        } catch (Exception e) {
            throw new ServiceException("Error inesperado al listar Vehicle",
                    ErrorCode.UNEXPECTED_ERROR, e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vehicle> search(VehicleSearchCriteria criteria, int offset, int limit) {
        try {
            return vehicleDAO.search(criteria, offset, limit);
        } catch (ServiceException e) {
            throw e;
        } catch (DAOException e) {
            throw new ServiceException("Error accediendo a datos en búsqueda de Vehicle",
                    ErrorCode.UNEXPECTED_ERROR, e);
        } catch (Exception e) {
            throw new ServiceException("Error inesperado en búsqueda de Vehicle",
                    ErrorCode.UNEXPECTED_ERROR, e);
        }
    }

    @Override
    public Long create(Vehicle entity) {
        try {
            if (vehicleDAO.existsByPlate(entity.getPlate())) {
                throw new PreValidationException(
                        "Ya existe un vehículo con patente " + entity.getPlate(),
                        ErrorCode.VEHICLE_PLATE_ALREADY_EXISTS);
            }

            Long id = vehicleDAO.save(entity);
            evictVehicleCaches(id, entity.getPlate());
            log.debug("Vehicle creado id={}, caches invalidados", id);
            return id;
        } catch (ServiceException e) {
            throw e;
        } catch (DAOException e) {
            throw new ServiceException("Error accediendo a datos al crear Vehicle",
                    ErrorCode.UNEXPECTED_ERROR, e);
        } catch (Exception e) {
            throw new ServiceException("Error inesperado al crear Vehicle",
                    ErrorCode.UNEXPECTED_ERROR, e);
        }
    }

    @Override
    public void update(Vehicle entity) {
        try {
            Vehicle existing = vehicleDAO.findById(entity.getId())
                    .orElseThrow(() -> new PreValidationException(
                            "No se encontró Vehicle con id=" + entity.getId(),
                            ErrorCode.VEHICLE_NOT_FOUND));

            String oldPlate = existing.getPlate();

            if (!oldPlate.equalsIgnoreCase(entity.getPlate())
                    && vehicleDAO.existsByPlate(entity.getPlate())) {
                throw new PreValidationException(
                        "Ya existe un vehículo con patente " + entity.getPlate(),
                        ErrorCode.VEHICLE_PLATE_ALREADY_EXISTS);
            }

            vehicleDAO.update(entity);

            evictVehicleCaches(entity.getId(), entity.getPlate());
            if (!oldPlate.equalsIgnoreCase(entity.getPlate())) {
                evictPlateCache(oldPlate);
            }
            log.debug("Vehicle actualizado id={}, caches invalidados", entity.getId());
        } catch (ServiceException e) {
            throw e;
        } catch (DAOException e) {
            throw new ServiceException("Error accediendo a datos al actualizar Vehicle",
                    ErrorCode.UNEXPECTED_ERROR, e);
        } catch (Exception e) {
            throw new ServiceException("Error inesperado al actualizar Vehicle",
                    ErrorCode.UNEXPECTED_ERROR, e);
        }
    }

    @Override
    public boolean softDelete(Long id) {
        try {
            Vehicle existing = vehicleDAO.findById(id)
                    .orElseThrow(() -> new PreValidationException(
                            "No se encontró Vehicle con id=" + id,
                            ErrorCode.VEHICLE_NOT_FOUND));

            boolean deleted = vehicleDAO.softDelete(id);

            if (deleted) {
                evictVehicleCaches(id, existing.getPlate());
                log.debug("Vehicle softDelete id={}, caches invalidados", id);
            }
            return deleted;
        } catch (ServiceException e) {
            throw e;
        } catch (DAOException e) {
            throw new ServiceException("Error accediendo a datos al eliminar Vehicle id=" + id,
                    ErrorCode.UNEXPECTED_ERROR, e);
        } catch (Exception e) {
            throw new ServiceException("Error inesperado al eliminar Vehicle id=" + id,
                    ErrorCode.UNEXPECTED_ERROR, e);
        }
    }

    private void evictVehicleCaches(Long id, String plate) {
        Cache byId = cacheManager.getCache(CacheConfig.VEHICLE_BY_ID);
        if (byId != null && id != null) {
            byId.evict(id);
        }
        evictPlateCache(plate);
    }

    private void evictPlateCache(String plate) {
        Cache byPlate = cacheManager.getCache(CacheConfig.VEHICLE_BY_PLATE);
        if (byPlate != null && plate != null) {
            byPlate.evict(plate.trim().toUpperCase());
        }
    }
}
