package com.myhotel.backendtest.service;

import com.myhotel.backendtest.dao.criteria.VehicleSearchCriteria;
import com.myhotel.backendtest.entity.Vehicle;
import com.myhotel.backendtest.exception.PreValidationException;
import com.myhotel.backendtest.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de negocio para la gestión de vehículos.
 * Proporciona operaciones CRUD y búsquedas con soporte de caché en memoria
 * para optimizar consultas frecuentes por ID y patente.
 *
 * @author Martin Lopez
 */
public interface VehicleService {

    /**
     * Obtiene un vehículo por su identificador único.
     * El resultado se cachea para consultas posteriores.
     *
     * @param id identificador único del vehículo
     * @return vehículo encontrado, o vacío si no existe
     * @throws ServiceException cuando ocurre un error al acceder a los datos
     */
    Optional<Vehicle> getById(Long id) throws ServiceException;

    /**
     * Obtiene un vehículo por su patente.
     * El resultado se cachea para consultas posteriores.
     *
     * @param plate patente del vehículo (no sensible a mayúsculas/minúsculas)
     * @return vehículo encontrado, o vacío si no existe
     * @throws ServiceException cuando ocurre un error al acceder a los datos
     */
    Optional<Vehicle> getByPlate(String plate) throws ServiceException;

    /**
     * Verifica si existe un vehículo con la patente especificada.
     * Consulta optimizada que no carga la entidad completa.
     *
     * @param plate patente a verificar
     * @return true si existe un vehículo con esa patente, false en caso contrario
     * @throws ServiceException cuando ocurre un error al acceder a los datos
     */
    boolean existsByPlate(String plate) throws ServiceException;

    /**
     * Lista vehículos con paginación simple.
     * Retorna solo vehículos no eliminados lógicamente, ordenados por ID.
     *
     * @param offset número de registros a omitir desde el inicio
     * @param limit número máximo de registros a retornar
     * @return lista de vehículos en el rango especificado
     * @throws ServiceException cuando ocurre un error al acceder a los datos
     */
    List<Vehicle> findAll(int offset, int limit) throws ServiceException;

    /**
     * Realiza una búsqueda avanzada de vehículos aplicando filtros opcionales.
     * Permite filtrar por marca, modelo, rango de años, tipo de vehículo y patente parcial.
     *
     * @param criteria criterios de búsqueda (campos opcionales)
     * @param offset número de registros a omitir desde el inicio
     * @param limit número máximo de registros a retornar
     * @return lista de vehículos que cumplen los criterios especificados
     * @throws ServiceException cuando ocurre un error al acceder a los datos
     */
    List<Vehicle> search(VehicleSearchCriteria criteria, int offset, int limit) throws ServiceException;

    /**
     * Crea un nuevo vehículo en el sistema.
     * Valida que la patente no esté duplicada antes de persistir.
     * Invalida los caches relacionados después de la creación.
     *
     * @param entity vehículo a crear (puede ser Car o Truck)
     * @return identificador único del vehículo creado
     * @throws PreValidationException cuando la patente ya existe en el sistema
     * @throws ServiceException cuando ocurre un error al acceder a los datos
     */
    Long create(Vehicle entity) throws PreValidationException, ServiceException;

    /**
     * Actualiza los datos de un vehículo existente.
     * Valida que el vehículo exista y que la nueva patente (si cambió) no esté duplicada.
     * Invalida los caches relacionados después de la actualización.
     *
     * @param entity vehículo con los datos actualizados (debe incluir el ID)
     * @throws PreValidationException cuando el vehículo no existe o la nueva patente está duplicada
     * @throws ServiceException cuando ocurre un error al acceder a los datos
     */
    void update(Vehicle entity) throws PreValidationException, ServiceException;

    /**
     * Elimina lógicamente un vehículo del sistema.
     * Marca el vehículo como eliminado sin borrarlo físicamente de la base de datos.
     * Invalida los caches relacionados después de la eliminación.
     *
     * @param id identificador único del vehículo a eliminar
     * @return true si el vehículo existía y fue marcado como eliminado, false si no se encontró
     * @throws PreValidationException cuando el vehículo no existe
     * @throws ServiceException cuando ocurre un error al acceder a los datos
     */
    boolean softDelete(Long id) throws PreValidationException, ServiceException;
}
