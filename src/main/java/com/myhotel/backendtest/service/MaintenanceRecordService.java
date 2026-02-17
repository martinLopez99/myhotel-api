package com.myhotel.backendtest.service;

import com.myhotel.backendtest.entity.MaintenanceRecord;
import com.myhotel.backendtest.exception.PreValidationException;
import com.myhotel.backendtest.exception.ServiceException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de negocio para la gestión de registros de mantenimiento de vehículos.
 * Proporciona operaciones CRUD y consultas especializadas con soporte de caché
 * para optimizar la consulta del último mantenimiento por vehículo.
 *
 * @author Martin Lopez
 */
public interface MaintenanceRecordService {

    /**
     * Obtiene un registro de mantenimiento por su identificador único.
     *
     * @param id identificador único del registro de mantenimiento
     * @return registro encontrado, o vacío si no existe
     * @throws ServiceException cuando ocurre un error al acceder a los datos
     */
    Optional<MaintenanceRecord> getById(Long id) throws ServiceException;

    /**
     * Lista los registros de mantenimiento de un vehículo específico.
     * Los resultados se ordenan por fecha de mantenimiento descendente (más reciente primero).
     *
     * @param vehicleId identificador único del vehículo
     * @param offset número de registros a omitir desde el inicio
     * @param limit número máximo de registros a retornar
     * @return lista de registros de mantenimiento del vehículo especificado
     * @throws PreValidationException cuando el vehículo no existe
     * @throws ServiceException cuando ocurre un error al acceder a los datos
     */
    List<MaintenanceRecord> findByVehicleId(Long vehicleId, int offset, int limit)
            throws PreValidationException, ServiceException;

    /**
     * Lista los registros de mantenimiento de un vehículo identificado por su patente.
     * Los resultados se ordenan por fecha de mantenimiento descendente (más reciente primero).
     *
     * @param plate patente del vehículo
     * @param offset número de registros a omitir desde el inicio
     * @param limit número máximo de registros a retornar
     * @return lista de registros de mantenimiento del vehículo especificado
     * @throws PreValidationException cuando el vehículo no existe
     * @throws ServiceException cuando ocurre un error al acceder a los datos
     */
    List<MaintenanceRecord> findByPlate(String plate, int offset, int limit)
            throws PreValidationException, ServiceException;

    /**
     * Obtiene el último registro de mantenimiento de un vehículo por su identificador.
     * El resultado se cachea para consultas posteriores.
     *
     * @param vehicleId identificador único del vehículo
     * @return último registro de mantenimiento encontrado, o vacío si no existe ninguno
     * @throws ServiceException cuando ocurre un error al acceder a los datos
     */
    Optional<MaintenanceRecord> getLatestByVehicleId(Long vehicleId) throws ServiceException;

    /**
     * Obtiene el último registro de mantenimiento de un vehículo por su patente.
     * El resultado se cachea para consultas posteriores.
     *
     * @param plate patente del vehículo
     * @return último registro de mantenimiento encontrado, o vacío si no existe ninguno
     * @throws ServiceException cuando ocurre un error al acceder a los datos
     */
    Optional<MaintenanceRecord> getLatestByPlate(String plate) throws ServiceException;

    /**
     * Verifica si ya existe un registro de mantenimiento para un vehículo en una fecha específica.
     * Consulta optimizada que no carga la entidad completa.
     *
     * @param vehicleId identificador único del vehículo
     * @param maintenanceAt fecha del mantenimiento a verificar
     * @return true si existe un registro para ese vehículo en esa fecha, false en caso contrario
     * @throws ServiceException cuando ocurre un error al acceder a los datos
     */
    boolean existsForVehicleOnDate(Long vehicleId, Instant maintenanceAt) throws ServiceException;

    /**
     * Crea un nuevo registro de mantenimiento en el sistema.
     * Valida que el vehículo exista, no esté eliminado y que el kilometraje del mantenimiento
     * no sea menor que el kilometraje actual del vehículo. Actualiza el kilometraje del vehículo
     * si el mantenimiento tiene un kilometraje mayor. Invalida los caches relacionados.
     *
     * @param entity registro de mantenimiento a crear (debe incluir la referencia al vehículo)
     * @return identificador único del registro creado
     * @throws PreValidationException cuando el vehículo no existe, está eliminado o el kilometraje es inválido
     * @throws ServiceException cuando ocurre un error al acceder a los datos
     */
    Long create(MaintenanceRecord entity) throws PreValidationException, ServiceException;

    /**
     * Actualiza los datos de un registro de mantenimiento existente.
     * Valida que el registro exista antes de actualizar. Invalida los caches relacionados,
     * incluyendo los del vehículo asociado y del vehículo anterior si cambió la asociación.
     *
     * @param entity registro de mantenimiento con los datos actualizados (debe incluir el ID)
     * @throws PreValidationException cuando el registro no existe
     * @throws ServiceException cuando ocurre un error al acceder a los datos
     */
    void update(MaintenanceRecord entity) throws PreValidationException, ServiceException;

    /**
     * Elimina lógicamente un registro de mantenimiento del sistema.
     * Solo permite eliminar el último mantenimiento del vehículo (el de mayor kilometraje).
     * Recalcula y actualiza el kilometraje del vehículo basándose en el nuevo último mantenimiento.
     * Invalida los caches relacionados después de la eliminación.
     *
     * @param id identificador único del registro de mantenimiento a eliminar
     * @param plate patente del vehículo al que pertenece el mantenimiento (para validación)
     * @return true si el registro existía y fue marcado como eliminado, false si no se encontró
     * @throws PreValidationException cuando el vehículo no existe, el registro no existe,
     *                                el registro no pertenece al vehículo especificado,
     *                                o el registro no es el último mantenimiento del vehículo
     * @throws ServiceException cuando ocurre un error al acceder a los datos
     */
    boolean softDelete(Long id, String plate) throws PreValidationException, ServiceException;
}
