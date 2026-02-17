package com.myhotel.backendtest.dao.impl;

import com.myhotel.backendtest.dao.MaintenanceRecordDAO;
import com.myhotel.backendtest.entity.MaintenanceRecord;
import com.myhotel.backendtest.exception.DAOException;
import com.myhotel.backendtest.exception.ErrorCode;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del DAO de registros de mantenimiento.
 * Usa Criteria API y hereda operaciones genéricas de GenericDAOImpl.
 * Cada método público aplica try/catch.
 *
 * @author Martin Lopez
 */
@Repository(MaintenanceRecordDAOImpl.BEAN_NAME)
public class MaintenanceRecordDAOImpl extends GenericDAOImpl<MaintenanceRecord>
        implements MaintenanceRecordDAO {

    public static final String BEAN_NAME = "MaintenanceRecordDAO";

    public MaintenanceRecordDAOImpl() {
        super(MaintenanceRecord.class);
    }

    /** {@inheritDoc} */
    @Override
    public List<MaintenanceRecord> findByVehicleId(Long vehicleId, int offset, int limit) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<MaintenanceRecord> cq = cb.createQuery(MaintenanceRecord.class);
            Root<MaintenanceRecord> root = cq.from(MaintenanceRecord.class);

            cq.select(root).where(
                    cb.equal(root.get("vehicle").get("id"), vehicleId),
                    cb.isFalse(root.get("deleted"))
            ).orderBy(cb.desc(root.get("maintenanceAt")));

            return em.createQuery(cq)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException(
                    "Error al listar mantenimientos del vehicleId=" + vehicleId,
                    ErrorCode.DAO_UNEXPECTED_ERROR, e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public List<MaintenanceRecord> findByPlate(String plate, int offset, int limit) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<MaintenanceRecord> cq = cb.createQuery(MaintenanceRecord.class);
            Root<MaintenanceRecord> root = cq.from(MaintenanceRecord.class);

            cq.select(root).where(
                    cb.equal(cb.upper(root.get("vehicle").get("plate")), plate.toUpperCase()),
                    cb.isFalse(root.get("deleted"))
            ).orderBy(cb.desc(root.get("maintenanceAt")));

            return em.createQuery(cq)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException(
                    "Error al listar mantenimientos de la patente=" + plate,
                    ErrorCode.DAO_UNEXPECTED_ERROR, e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public Optional<MaintenanceRecord> findLatestByVehicleId(Long vehicleId) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<MaintenanceRecord> cq = cb.createQuery(MaintenanceRecord.class);
            Root<MaintenanceRecord> root = cq.from(MaintenanceRecord.class);

            cq.select(root).where(
                    cb.equal(root.get("vehicle").get("id"), vehicleId),
                    cb.isFalse(root.get("deleted"))
            ).orderBy(cb.desc(root.get("maintenanceAt")));

            return em.createQuery(cq)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst();
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException(
                    "Error al buscar último mantenimiento del vehicleId=" + vehicleId,
                    ErrorCode.DAO_UNEXPECTED_ERROR, e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public Optional<MaintenanceRecord> findLatestByPlate(String plate) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<MaintenanceRecord> cq = cb.createQuery(MaintenanceRecord.class);
            Root<MaintenanceRecord> root = cq.from(MaintenanceRecord.class);

            cq.select(root).where(
                    cb.equal(cb.upper(root.get("vehicle").get("plate")), plate.toUpperCase()),
                    cb.isFalse(root.get("deleted"))
            ).orderBy(cb.desc(root.get("maintenanceAt")));

            return em.createQuery(cq)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst();
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException(
                    "Error al buscar último mantenimiento de la patente=" + plate,
                    ErrorCode.DAO_UNEXPECTED_ERROR, e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public Optional<MaintenanceRecord> findLatestByVehicleIdOrderByMileage(Long vehicleId) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<MaintenanceRecord> cq = cb.createQuery(MaintenanceRecord.class);
            Root<MaintenanceRecord> root = cq.from(MaintenanceRecord.class);

            cq.select(root).where(
                    cb.equal(root.get("vehicle").get("id"), vehicleId),
                    cb.isFalse(root.get("deleted"))
            ).orderBy(
                    cb.desc(root.get("currentKm")),
                    cb.desc(root.get("maintenanceAt")),
                    cb.desc(root.get("id"))
            );

            return em.createQuery(cq)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst();
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException(
                    "Error al buscar último mantenimiento por kilometraje del vehicleId=" + vehicleId,
                    ErrorCode.DAO_UNEXPECTED_ERROR, e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean existsForVehicleOnDate(Long vehicleId, Instant maintenanceAt) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);
            Root<MaintenanceRecord> root = cq.from(MaintenanceRecord.class);

            cq.select(cb.literal(1)).where(
                    cb.equal(root.get("vehicle").get("id"), vehicleId),
                    cb.equal(root.get("maintenanceAt"), maintenanceAt),
                    cb.isFalse(root.get("deleted"))
            );

            return !em.createQuery(cq).setMaxResults(1).getResultList().isEmpty();
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException(
                    "Error al verificar existencia de mantenimiento para vehicleId=" + vehicleId,
                    ErrorCode.DAO_UNEXPECTED_ERROR, e);
        }
    }
}
