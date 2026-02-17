package com.myhotel.backendtest.dao.impl;

import com.myhotel.backendtest.dao.GenericDAO;
import com.myhotel.backendtest.entity.BaseEntity;
import com.myhotel.backendtest.exception.DAOException;
import com.myhotel.backendtest.exception.ErrorCode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.util.List;
import java.util.Optional;

/**
 * Implementación abstracta genérica del DAO.
 * Centraliza operaciones CRUD comunes usando Criteria API sobre entidades que extienden BaseEntity.
 * Cada método público aplica try/catch
 *
 * @param <T> tipo de la entidad (debe extender BaseEntity)
 * @author Martin Lopez
 */
public abstract class GenericDAOImpl<T extends BaseEntity> implements GenericDAO<T> {

    @PersistenceContext
    protected EntityManager em;

    private final Class<T> entityClass;

    protected GenericDAOImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public Optional<T> findById(Long id) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(entityClass);
            Root<T> root = cq.from(entityClass);

            cq.select(root).where(
                    cb.equal(root.get("id"), id),
                    cb.isFalse(root.get("deleted"))
            );

            return em.createQuery(cq).getResultStream().findFirst();
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException(
                    "Error al buscar " + entityClass.getSimpleName() + " por id=" + id,
                    ErrorCode.DAO_UNEXPECTED_ERROR, e);
        }
    }

    @Override
    public Long save(T entity) {
        try {
            em.persist(entity);
            em.flush();
            return entity.getId();
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException(
                    "Error al persistir " + entityClass.getSimpleName(),
                    ErrorCode.DAO_UNEXPECTED_ERROR, e);
        }
    }

    @Override
    public void update(T entity) {
        try {
            em.merge(entity);
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException(
                    "Error al actualizar " + entityClass.getSimpleName(),
                    ErrorCode.DAO_UNEXPECTED_ERROR, e);
        }
    }

    @Override
    public boolean softDelete(Long id) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaUpdate<T> cu = cb.createCriteriaUpdate(entityClass);
            Root<T> root = cu.from(entityClass);

            cu.set(root.<Boolean>get("deleted"), true);
            cu.where(
                    cb.equal(root.get("id"), id),
                    cb.isFalse(root.get("deleted"))
            );

            return em.createQuery(cu).executeUpdate() > 0;
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException(
                    "Error al eliminar lógicamente " + entityClass.getSimpleName() + " id=" + id,
                    ErrorCode.DAO_UNEXPECTED_ERROR, e);
        }
    }

    @Override
    public List<T> findAll(int offset, int limit) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(entityClass);
            Root<T> root = cq.from(entityClass);

            cq.select(root)
                    .where(cb.isFalse(root.get("deleted")))
                    .orderBy(cb.asc(root.get("id")));

            return em.createQuery(cq)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException(
                    "Error al listar " + entityClass.getSimpleName(),
                    ErrorCode.DAO_UNEXPECTED_ERROR, e);
        }
    }

    @Override
    public boolean existsById(Long id) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);
            Root<T> root = cq.from(entityClass);

            cq.select(cb.literal(1)).where(
                    cb.equal(root.get("id"), id),
                    cb.isFalse(root.get("deleted"))
            );

            return !em.createQuery(cq).setMaxResults(1).getResultList().isEmpty();
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException(
                    "Error al verificar existencia de " + entityClass.getSimpleName() + " id=" + id,
                    ErrorCode.DAO_UNEXPECTED_ERROR, e);
        }
    }
}
