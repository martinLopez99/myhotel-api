package com.myhotel.backendtest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Entidad padre abstracta para vehículos. Estrategia SINGLE_TABLE:
 * Car y Truck se persisten en la misma tabla con discriminador DTYPE.
 *
 * @author Martin Lopez
 */
@Entity
@Table(name = "VEHICLE", uniqueConstraints = @UniqueConstraint(name = "uk_vehicle_plate", columnNames = "plate"))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE", length = 31)
@Getter
@Setter
public abstract class Vehicle extends BaseEntity {

    @NotBlank
    @Column(nullable = false, length = 100)
    private String brand;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String model;

    @NotNull
    @Column(nullable = false)
    private Integer year;

    @NotBlank
    @Column(nullable = false, unique = true, length = 20)
    private String plate;

    @NotNull
    @Column(name = "mileage_km", nullable = false)
    private Integer mileageKm;

    @NotNull
    @Column(name = "engine_displacement_cc", nullable = false)
    private Integer engineDisplacementCc;

    @Column(name = "last_maintenance_at")
    private Instant lastMaintenanceAt;

    @Column(name = "last_maintenance_cost", precision = 10, scale = 2)
    private BigDecimal lastMaintenanceCost;

    @Column(name = "last_maintenance_by", length = 120)
    private String lastMaintenanceBy;
}
