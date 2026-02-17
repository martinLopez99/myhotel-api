package com.myhotel.backendtest.dto.out;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO de salida para vehículos.
 * Incluye solo campos de negocio (sin createdAt, updatedAt, version).
 * Incluye deleted para indicar si el vehículo está eliminado lógicamente.
 *
 * @author Martin Lopez
 */
@Getter
@Setter
public class VehicleDTOOut {

    private Long id;
    private String type;

    // ── Campos comunes ──────────────────────────────────────────────────────
    private String brand;
    private String model;
    private Integer year;
    private String plate;
    private Integer mileageKm;
    private Integer engineDisplacementCc;
    private Boolean deleted;

    // ── Campos Car ──────────────────────────────────────────────────────────
    private String carType;
    private Integer numberOfDoors;
    private Integer passengerCapacity;
    private Integer trunkCapacityLiters;

    // ── Campos Truck ────────────────────────────────────────────────────────
    private String truckType;
    private Integer loadCapacityTons;
    private Integer axles;
}
