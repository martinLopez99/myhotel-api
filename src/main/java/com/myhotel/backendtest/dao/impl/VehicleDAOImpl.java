package com.myhotel.backendtest.dao.impl;

import com.myhotel.backendtest.dao.VehicleDAO;
import com.myhotel.backendtest.dao.criteria.VehicleSearchCriteria;
import com.myhotel.backendtest.entity.Car;
import com.myhotel.backendtest.entity.Truck;
import com.myhotel.backendtest.entity.Vehicle;
import com.myhotel.backendtest.exception.DAOException;
import com.myhotel.backendtest.exception.ErrorCode;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del DAO de vehículos.
 * Usa Criteria API para consultas dinámicas y hereda operaciones genéricas de GenericDAOImpl.
 * Cada método público aplica try/catch → DAOException.
 *
 * @author Martin Lopez
 */
@Repository(VehicleDAOImpl.BEAN_NAME)
public class VehicleDAOImpl extends GenericDAOImpl<Vehicle> implements VehicleDAO {

    public static final String BEAN_NAME = "VehicleDAO";

    public VehicleDAOImpl() {
        super(Vehicle.class);
    }

    /** {@inheritDoc} */
    @Override
    public Optional<Vehicle> findByIdIncludingDeleted(Long id) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Vehicle> cq = cb.createQuery(Vehicle.class);
            Root<Vehicle> root = cq.from(Vehicle.class);

            cq.select(root).where(
                    cb.equal(root.get("id"), id)
            );

            return em.createQuery(cq).getResultStream().findFirst();
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException(
                    "Error al buscar Vehicle por id=" + id + " (incluyendo eliminados)",
                    ErrorCode.DAO_UNEXPECTED_ERROR, e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public Optional<Vehicle> findByPlate(String plate) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Vehicle> cq = cb.createQuery(Vehicle.class);
            Root<Vehicle> root = cq.from(Vehicle.class);

            cq.select(root).where(
                    cb.equal(root.get("plate"), plate),
                    cb.isFalse(root.get("deleted"))
            );

            return em.createQuery(cq).getResultStream().findFirst();
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException(
                    "Error al buscar Vehicle por plate=" + plate,
                    ErrorCode.DAO_UNEXPECTED_ERROR, e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean existsByPlate(String plate) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);
            Root<Vehicle> root = cq.from(Vehicle.class);

            cq.select(cb.literal(1)).where(
                    cb.equal(root.get("plate"), plate),
                    cb.isFalse(root.get("deleted"))
            );

            return !em.createQuery(cq).setMaxResults(1).getResultList().isEmpty();
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException(
                    "Error al verificar existencia de plate=" + plate,
                    ErrorCode.DAO_UNEXPECTED_ERROR, e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public List<Vehicle> search(VehicleSearchCriteria criteria, int offset, int limit) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Vehicle> cq = cb.createQuery(Vehicle.class);
            Root<Vehicle> root = cq.from(Vehicle.class);

            List<Predicate> predicates = buildPredicates(cb, root, criteria);

            cq.select(root)
                    .where(predicates.toArray(Predicate[]::new))
                    .orderBy(cb.asc(root.get("id")));

            return em.createQuery(cq)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException(
                    "Error en búsqueda avanzada de Vehicle",
                    ErrorCode.DAO_UNEXPECTED_ERROR, e);
        }
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private List<Predicate> buildPredicates(CriteriaBuilder cb, Root<Vehicle> root,
                                            VehicleSearchCriteria c) {
        List<Predicate> predicates = new ArrayList<>();

        // Soft delete: por defecto excluir eliminados
        if (c.getDeleted() == null || Boolean.FALSE.equals(c.getDeleted())) {
            predicates.add(cb.isFalse(root.get("deleted")));
        } else {
            predicates.add(cb.isTrue(root.get("deleted")));
        }

        if (c.getBrand() != null && !c.getBrand().isBlank()) {
            predicates.add(cb.equal(
                    cb.lower(root.get("brand")),
                    c.getBrand().toLowerCase()
            ));
        }

        if (c.getModel() != null && !c.getModel().isBlank()) {
            predicates.add(cb.like(
                    cb.lower(root.get("model")),
                    "%" + c.getModel().toLowerCase() + "%"
            ));
        }

        if (c.getYearFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("year"), c.getYearFrom()));
        }

        if (c.getYearTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("year"), c.getYearTo()));
        }

        if (c.getDtype() != null && !c.getDtype().isBlank()) {
            predicates.add(cb.equal(root.type(), getDiscriminatorClass(c.getDtype())));
        }

        if (c.getPlateLike() != null && !c.getPlateLike().isBlank()) {
            predicates.add(cb.like(
                    cb.lower(root.get("plate")),
                    "%" + c.getPlateLike().toLowerCase() + "%"
            ));
        }

        return predicates;
    }

    /**
     * Resuelve la subclase concreta a partir del nombre del discriminador.
     */
    private Class<? extends Vehicle> getDiscriminatorClass(String dtype) {
        return switch (dtype.toUpperCase()) {
            case "CAR" -> Car.class;
            case "TRUCK" -> Truck.class;
            default -> throw new DAOException(
                    "Tipo de vehículo desconocido: " + dtype,
                    ErrorCode.VEHICLE_INVALID_TYPE);
        };
    }
}
