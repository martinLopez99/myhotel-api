package com.myhotel.backendtest.dto.in;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO de entrada para crear/actualizar un vehículo.
 * Un solo DTO cubre Car y Truck; el campo {@code type} discrimina el subtipo.
 * Los campos específicos de cada subtipo son opcionales (nullable).
 *
 * @author Martin Lopez
 */
@Getter
@Setter
public class VehicleDTOIn {

    // ── Campos comunes (obligatorios) ────────────────────────────────────────

    @NotBlank(message = "El tipo de vehículo es obligatorio (Car / Truck)")
    private String type;

    @NotBlank(message = "La marca es obligatoria")
    private String brand;

    @NotBlank(message = "El modelo es obligatorio")
    private String model;

    @NotNull(message = "El año es obligatorio")
    @Min(value = 1900, message = "El año debe ser mayor o igual a 1900")
    private Integer year;

    @NotBlank(message = "La patente es obligatoria")
    private String plate;

    @NotNull(message = "El kilometraje es obligatorio")
    @Min(value = 0, message = "El kilometraje no puede ser negativo")
    private Integer mileageKm;

    @NotNull(message = "La cilindrada es obligatoria")
    @Min(value = 1, message = "La cilindrada debe ser mayor a 0")
    private Integer engineDisplacementCc;

    // ── Campos específicos de Car (opcionales) ──────────────────────────────

    /** Tipo de automóvil (hatchback, sedan, etc.) */
    private String carType;

    @Min(value = 1, message = "El número de puertas debe ser al menos 1")
    private Integer numberOfDoors;

    @Min(value = 1, message = "La capacidad de pasajeros debe ser al menos 1")
    private Integer passengerCapacity;

    @Min(value = 0, message = "La capacidad del maletero no puede ser negativa")
    private Integer trunkCapacityLiters;

    // ── Campos específicos de Truck (opcionales) ────────────────────────────

    /** Tipo de camión (¾, tolva, etc.) */
    private String truckType;

    @Min(value = 1, message = "La capacidad en toneladas debe ser al menos 1")
    private Integer loadCapacityTons;

    @Min(value = 2, message = "La cantidad de ejes debe ser al menos 2")
    private Integer axles;
}
