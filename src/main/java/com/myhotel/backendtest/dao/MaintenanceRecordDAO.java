package com.myhotel.backendtest.dao;

import com.myhotel.backendtest.entity.MaintenanceRecord;
import com.myhotel.backendtest.exception.DAOException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * DAO específico para la entidad MaintenanceRecord.
 *
 * @author Martin Lopez
 */
public interface MaintenanceRecordDAO extends GenericDAO<MaintenanceRecord> {

    /**
     * Lista los registros de mantenimiento de un vehículo, ordenados por fecha descendente.
     */
    List<MaintenanceRecord> findByVehicleId(Long vehicleId, int offset, int limit) throws DAOException;

    /**
     * Lista los registros de mantenimiento de un vehículo por patente, ordenados por fecha descendente.
     */
    List<MaintenanceRecord> findByPlate(String plate, int offset, int limit) throws DAOException;

    /**
     * Obtiene el último mantenimiento registrado para un vehículo (max maintenanceAt).
     */
    Optional<MaintenanceRecord> findLatestByVehicleId(Long vehicleId) throws DAOException;

    /**
     * Obtiene el último mantenimiento registrado para un vehículo por patente (max maintenanceAt).
     */
    Optional<MaintenanceRecord> findLatestByPlate(String plate) throws DAOException;

    /**
     * Obtiene el último mantenimiento de un vehículo ordenado por mileageKm desc, maintenanceAt desc, id desc.
     * Útil para determinar qué mantenimiento tiene el mayor kilometraje.
     */
    Optional<MaintenanceRecord> findLatestByVehicleIdOrderByMileage(Long vehicleId) throws DAOException;

    /**
     * Verifica si ya existe un registro de mantenimiento para un vehículo en una fecha dada (SELECT 1).
     */
    boolean existsForVehicleOnDate(Long vehicleId, Instant maintenanceAt) throws DAOException;
}
