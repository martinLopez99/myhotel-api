package com.myhotel.backendtest.dao;

import com.myhotel.backendtest.exception.DAOException;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz genérica de acceso a datos.
 * Provee operaciones CRUD comunes para cualquier entidad que extienda BaseEntity.
 *
 * @param <T> tipo de la entidad
 * @author Martin Lopez
 */
public interface GenericDAO<T> {

    /**
     * Busca una entidad por su ID (no eliminada lógicamente).
     */
    Optional<T> findById(Long id) throws DAOException;

    /**
     * Persiste una nueva entidad y retorna su ID generado.
     */
    Long save(T entity) throws DAOException;

    /**
     * Actualiza una entidad existente (merge).
     */
    void update(T entity) throws DAOException;

    /**
     * Eliminación lógica: marca deleted = true.
     *
     * @return true si la entidad existía y fue marcada; false si no se encontró.
     */
    boolean softDelete(Long id) throws DAOException;

    /**
     * Lista entidades no eliminadas con paginación simple (offset / limit).
     */
    List<T> findAll(int offset, int limit) throws DAOException;

    /**
     * Verifica si existe una entidad con el ID dado (no eliminada).
     */
    boolean existsById(Long id) throws DAOException;
}
