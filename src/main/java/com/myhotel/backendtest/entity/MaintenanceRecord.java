package com.myhotel.backendtest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Registro de mantenimiento asociado a un vehículo.
 *
 * @author Martin Lopez
 */
@Entity
@Table(name = "maintenance_record", indexes = {
        @Index(name = "ix_maintenance_vehicle", columnList = "vehicle_id"),
        @Index(name = "ix_maintenance_vehicle_date", columnList = "vehicle_id, maintenance_at")
})
@Getter
@Setter
public class MaintenanceRecord extends BaseEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @NotNull
    @Column(name = "maintenance_at", nullable = false)
    private Instant maintenanceAt;

    @NotNull
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cost;

    @Column(name = "performed_by", length = 120)
    private String performedBy;

    @Column(length = 255)
    private String notes;

    @NotNull
    @Column(name = "current_km", nullable = false)
    private Integer currentKm;
}
