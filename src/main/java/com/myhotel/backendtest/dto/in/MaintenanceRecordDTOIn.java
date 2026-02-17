package com.myhotel.backendtest.dto.in;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO de entrada para crear/actualizar un registro de mantenimiento.
 *
 * @author Martin Lopez
 */
@Getter
@Setter
public class MaintenanceRecordDTOIn {

    @NotNull(message = "La fecha de mantenimiento es obligatoria")
    private Instant maintenanceAt;

    @NotNull(message = "El costo es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El costo no puede ser negativo")
    private BigDecimal cost;

    private String performedBy;

    private String notes;

    @NotNull(message = "El kilometraje actual es obligatorio")
    @Min(value = 0, message = "El kilometraje actual no puede ser negativo")
    private Integer currentKm;
}
