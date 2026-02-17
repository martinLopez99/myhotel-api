package com.myhotel.backendtest.service.impl;

import com.myhotel.backendtest.config.CacheConfig;
import com.myhotel.backendtest.dao.MaintenanceRecordDAO;
import com.myhotel.backendtest.dao.VehicleDAO;
import com.myhotel.backendtest.entity.MaintenanceRecord;
import com.myhotel.backendtest.entity.Vehicle;
import com.myhotel.backendtest.exception.*;
import com.myhotel.backendtest.service.MaintenanceRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de registros de mantenimiento con caching en memoria.
 * Solo se cachea el último mantenimiento por vehículo; escrituras invalidan ese cache
 * y los caches de Vehicle (por campos desnormalizados lastMaintenance*).
 * Aplica try/catch → ServiceException.
 *
 * @author Martin Lopez
 */
@Service
@Transactional
public class MaintenanceRecordServiceImpl implements MaintenanceRecordService {

    private static final Logger log = LoggerFactory.getLogger(MaintenanceRecordServiceImpl.class);

    @Autowired
    private MaintenanceRecordDAO maintenanceRecordDAO;
    
    @Autowired
    private VehicleDAO vehicleDAO;
    
    @Autowired
    private CacheManager cacheManager;


    @Override
    @Transactional(readOnly = true)
    public Optional<MaintenanceRecord> getById(Long id) {
        try {
            return maintenanceRecordDAO.findById(id);
        } catch (ServiceException e) {
            throw e;
        } catch (DAOException e) {
            throw new ServiceException("Error accediendo a datos al buscar MaintenanceRecord id=" + id,
                    ErrorCode.UNEXPECTED_ERROR, e);
        } catch (Exception e) {
            throw new ServiceException("Error inesperado al buscar MaintenanceRecord id=" + id,
                    ErrorCode.UNEXPECTED_ERROR, e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceRecord> findByVehicleId(Long vehicleId, int offset, int limit) {
        try {
            if (!vehicleDAO.existsById(vehicleId)) {
                throw new PreValidationException(
                        "No se encontró Vehicle con id=" + vehicleId,
                        ErrorCode.VEHICLE_NOT_FOUND);
            }

            return maintenanceRecordDAO.findByVehicleId(vehicleId, offset, limit);
        } catch (ServiceException e) {
            throw e;
        } catch (DAOException e) {
            throw new ServiceException("Error accediendo a datos al listar mantenimientos del vehicleId=" + vehicleId,
                    ErrorCode.UNEXPECTED_ERROR, e);
        } catch (Exception e) {
            throw new ServiceException("Error inesperado al listar mantenimientos del vehicleId=" + vehicleId,
                    ErrorCode.UNEXPECTED_ERROR, e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceRecord> findByPlate(String plate, int offset, int limit) {
        try {
            if (!vehicleDAO.existsByPlate(plate)) {
                throw new PreValidationException(
                        "No se encontró Vehicle con patente=" + plate,
                        ErrorCode.VEHICLE_NOT_FOUND);
            }

            return maintenanceRecordDAO.findByPlate(plate, offset, limit);
        } catch (ServiceException e) {
            throw e;
        } catch (DAOException e) {
            throw new ServiceException("Error accediendo a datos al listar mantenimientos de la patente=" + plate,
                    ErrorCode.UNEXPECTED_ERROR, e);
        } catch (Exception e) {
            throw new ServiceException("Error inesperado al listar mantenimientos de la patente=" + plate,
                    ErrorCode.UNEXPECTED_ERROR, e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheConfig.LATEST_MAINTENANCE_BY_VEHICLE,
               key = "#vehicleId")
    public Optional<MaintenanceRecord> getLatestByVehicleId(Long vehicleId) {
        try {
            log.debug("Cache MISS – cargando último mantenimiento vehicleId={} desde DB", vehicleId);
            return maintenanceRecordDAO.findLatestByVehicleId(vehicleId);
        } catch (ServiceException e) {
            throw e;
        } catch (DAOException e) {
            throw new ServiceException("Error accediendo a datos al buscar último mantenimiento vehicleId=" + vehicleId,
                    ErrorCode.UNEXPECTED_ERROR, e);
        } catch (Exception e) {
            throw new ServiceException("Error inesperado al buscar último mantenimiento vehicleId=" + vehicleId,
                    ErrorCode.UNEXPECTED_ERROR, e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheConfig.LATEST_MAINTENANCE_BY_VEHICLE,
               key = "#plate != null ? #plate.trim().toUpperCase() : null")
    public Optional<MaintenanceRecord> getLatestByPlate(String plate) {
        try {
            log.debug("Cache MISS – cargando último mantenimiento patente='{}' desde DB", plate);
            return maintenanceRecordDAO.findLatestByPlate(plate);
        } catch (ServiceException e) {
            throw e;
        } catch (DAOException e) {
            throw new ServiceException("Error accediendo a datos al buscar último mantenimiento patente=" + plate,
                    ErrorCode.UNEXPECTED_ERROR, e);
        } catch (Exception e) {
            throw new ServiceException("Error inesperado al buscar último mantenimiento patente=" + plate,
                    ErrorCode.UNEXPECTED_ERROR, e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsForVehicleOnDate(Long vehicleId, Instant maintenanceAt) {
        try {
            return maintenanceRecordDAO.existsForVehicleOnDate(vehicleId, maintenanceAt);
        } catch (ServiceException e) {
            throw e;
        } catch (DAOException e) {
            throw new ServiceException("Error accediendo a datos al verificar mantenimiento existente",
                    ErrorCode.UNEXPECTED_ERROR, e);
        } catch (Exception e) {
            throw new ServiceException("Error inesperado al verificar mantenimiento existente",
                    ErrorCode.UNEXPECTED_ERROR, e);
        }
    }


    @Override
    public Long create(MaintenanceRecord entity) {
        try {
            Vehicle vehicle = entity.getVehicle();
            if (vehicle == null || vehicle.getId() == null) {
                throw new PreValidationException(
                        "No se encontró Vehicle para asociar al mantenimiento",
                        ErrorCode.VEHICLE_NOT_FOUND);
            }

            Vehicle existingVehicle = vehicleDAO.findById(vehicle.getId())
                    .orElseThrow(() -> new PreValidationException(
                            "No se encontró Vehicle con id=" + vehicle.getId(),
                            ErrorCode.VEHICLE_NOT_FOUND));

            if (existingVehicle.isDeleted()) {
                throw new PreValidationException(
                        "No se puede crear mantenimiento para un vehículo eliminado (id=" + existingVehicle.getId() + ")",
                        ErrorCode.VEHICLE_DELETED);
            }

            Integer maintenanceKm = entity.getCurrentKm();
            Integer vehicleKm = existingVehicle.getMileageKm();
            if (maintenanceKm < vehicleKm) {
                throw new PreValidationException(
                        String.format("El kilometraje del mantenimiento (%d km) no puede ser menor que el kilometraje actual del vehículo (%d km)",
                                maintenanceKm, vehicleKm),
                        ErrorCode.MAINTENANCE_MILEAGE_LESS_THAN_VEHICLE);
            }

            Long id = maintenanceRecordDAO.save(entity);

            if (maintenanceKm > vehicleKm) {
                existingVehicle.setMileageKm(maintenanceKm);
                vehicleDAO.update(existingVehicle);
                log.debug("Vehicle.mileageKm actualizado de {} a {} km para vehicleId={}",
                        vehicleKm, maintenanceKm, existingVehicle.getId());
            }

            evictMaintenanceAndVehicleCaches(existingVehicle);

            log.debug("MaintenanceRecord creado id={}, caches invalidados para vehicleId={}",
                    id, existingVehicle.getId());
            return id;
        } catch (ServiceException e) {
            throw e;
        } catch (DAOException e) {
            throw new ServiceException("Error accediendo a datos al crear MaintenanceRecord",
                    ErrorCode.UNEXPECTED_ERROR, e);
        } catch (Exception e) {
            throw new ServiceException("Error inesperado al crear MaintenanceRecord",
                    ErrorCode.UNEXPECTED_ERROR, e);
        }
    }

    @Override
    public void update(MaintenanceRecord entity) {
        try {
            MaintenanceRecord existing = maintenanceRecordDAO.findById(entity.getId())
                    .orElseThrow(() -> new PreValidationException(
                            "No se encontró MaintenanceRecord con id=" + entity.getId(),
                            ErrorCode.MAINTENANCE_NOT_FOUND));

            Long oldVehicleId = existing.getVehicle().getId();
            String oldVehiclePlate = existing.getVehicle().getPlate();

            maintenanceRecordDAO.update(entity);

            Vehicle newVehicle = entity.getVehicle();
            evictMaintenanceAndVehicleCaches(newVehicle);

            if (!oldVehicleId.equals(newVehicle.getId())) {
                evictLatestMaintenanceCache(oldVehicleId);
                evictVehicleCaches(oldVehicleId, oldVehiclePlate);
            }

            log.debug("MaintenanceRecord actualizado id={}, caches invalidados para vehicleId={}",
                    entity.getId(), newVehicle.getId());
        } catch (ServiceException e) {
            throw e;
        } catch (DAOException e) {
            throw new ServiceException("Error accediendo a datos al actualizar MaintenanceRecord",
                    ErrorCode.UNEXPECTED_ERROR, e);
        } catch (Exception e) {
            throw new ServiceException("Error inesperado al actualizar MaintenanceRecord",
                    ErrorCode.UNEXPECTED_ERROR, e);
        }
    }

    @Override
    public boolean softDelete(Long id, String plate) {
        try {
            Vehicle vehicleByPlate = vehicleDAO.findByPlate(plate)
                    .orElseThrow(() -> new PreValidationException(
                            "No se encontró Vehicle con patente=" + plate,
                            ErrorCode.VEHICLE_NOT_FOUND));

            MaintenanceRecord existing = maintenanceRecordDAO.findById(id)
                    .orElseThrow(() -> new PreValidationException(
                            "No se encontró MaintenanceRecord con id=" + id,
                            ErrorCode.MAINTENANCE_NOT_FOUND));

            Vehicle vehicle = existing.getVehicle();
            Long vehicleId = vehicle.getId();

            if (!vehicle.getId().equals(vehicleByPlate.getId())) {
                throw new PreValidationException(
                        String.format("El mantenimiento con id=%d pertenece a un vehículo diferente. " +
                                "El vehículo del mantenimiento tiene patente '%s', pero se proporcionó patente '%s'.",
                                id, vehicle.getPlate(), plate),
                        ErrorCode.VEHICLE_NOT_FOUND);
            }

            Optional<MaintenanceRecord> latest = maintenanceRecordDAO.findLatestByVehicleIdOrderByMileage(vehicleId);

            if (latest.isEmpty() || !latest.get().getId().equals(id)) {
                throw new PreValidationException(
                        "Solo se puede eliminar el último mantenimiento del vehículo. " +
                        "El mantenimiento con id=" + id + " no es el último mantenimiento.",
                        ErrorCode.MAINTENANCE_DELETE_ONLY_LATEST_ALLOWED);
            }

            boolean deleted = maintenanceRecordDAO.softDelete(id);

            if (deleted) {
                Optional<MaintenanceRecord> latestAgain = maintenanceRecordDAO.findLatestByVehicleIdOrderByMileage(vehicleId);

                if (latestAgain.isPresent()) {
                    vehicle.setMileageKm(latestAgain.get().getCurrentKm());
                    log.debug("Vehicle.mileageKm actualizado a {} km (del nuevo último mantenimiento id={}) para vehicleId={}",
                            latestAgain.get().getCurrentKm(), latestAgain.get().getId(), vehicleId);
                } else {
                    vehicle.setMileageKm(0);
                    log.debug("Vehicle.mileageKm actualizado a 0 (no hay más mantenimientos) para vehicleId={}", vehicleId);
                }

                vehicleDAO.update(vehicle);

                evictMaintenanceAndVehicleCaches(vehicle);

                log.debug("MaintenanceRecord softDelete id={}, vehicle.mileageKm actualizado, caches invalidados para vehicleId={}",
                        id, vehicleId);
            }
            return deleted;
        } catch (ServiceException e) {
            throw e;
        } catch (DAOException e) {
            throw new ServiceException("Error accediendo a datos al eliminar MaintenanceRecord id=" + id,
                    ErrorCode.UNEXPECTED_ERROR, e);
        } catch (Exception e) {
            throw new ServiceException("Error inesperado al eliminar MaintenanceRecord id=" + id,
                    ErrorCode.UNEXPECTED_ERROR, e);
        }
    }


    /**
     * Invalida cache de último mantenimiento Y caches de Vehicle
     * (por campos desnormalizados lastMaintenance* en Vehicle).
     */
    private void evictMaintenanceAndVehicleCaches(Vehicle vehicle) {
        if (vehicle == null) return;
        evictLatestMaintenanceCache(vehicle.getId());
        evictLatestMaintenanceCacheByPlate(vehicle.getPlate());
        evictVehicleCaches(vehicle.getId(), vehicle.getPlate());
    }

    private void evictLatestMaintenanceCache(Long vehicleId) {
        Cache cache = cacheManager.getCache(CacheConfig.LATEST_MAINTENANCE_BY_VEHICLE);
        if (cache != null && vehicleId != null) {
            cache.evict(vehicleId);
        }
    }

    private void evictLatestMaintenanceCacheByPlate(String plate) {
        Cache cache = cacheManager.getCache(CacheConfig.LATEST_MAINTENANCE_BY_VEHICLE);
        if (cache != null && plate != null) {
            cache.evict(plate.trim().toUpperCase());
        }
    }

    private void evictVehicleCaches(Long vehicleId, String plate) {
        Cache byId = cacheManager.getCache(CacheConfig.VEHICLE_BY_ID);
        if (byId != null && vehicleId != null) {
            byId.evict(vehicleId);
        }
        Cache byPlate = cacheManager.getCache(CacheConfig.VEHICLE_BY_PLATE);
        if (byPlate != null && plate != null) {
            byPlate.evict(plate.trim().toUpperCase());
        }
    }
}
