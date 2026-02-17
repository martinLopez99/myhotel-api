package com.myhotel.backendtest.dao;

import com.myhotel.backendtest.dao.criteria.VehicleSearchCriteria;
import com.myhotel.backendtest.entity.Vehicle;
import com.myhotel.backendtest.exception.DAOException;

import java.util.List;
import java.util.Optional;

/**
 * DAO específico para la entidad Vehicle y sus subtipos (Car, Truck).
 *
 * @author Martin Lopez
 */
public interface VehicleDAO extends GenericDAO<Vehicle> {

    /**
     * Busca un vehículo por ID incluyendo eliminados (deleted=true).
     * Útil para GET by ID que debe devolver el registro aunque esté eliminado.
     */
    Optional<Vehicle> findByIdIncludingDeleted(Long id) throws DAOException;

    /**
     * Busca un vehículo por su patente (no eliminado).
     */
    Optional<Vehicle> findByPlate(String plate) throws DAOException;

    /**
     * Verifica existencia de patente con SELECT 1 (eficiente, sin cargar entidad).
     */
    boolean existsByPlate(String plate) throws DAOException;

    /**
     * Búsqueda avanzada con filtros opcionales y paginación.
     */
    List<Vehicle> search(VehicleSearchCriteria criteria, int offset, int limit) throws DAOException;
}
