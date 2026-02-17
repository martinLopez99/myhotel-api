package com.myhotel.backendtest.dto.out;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO de salida para registros de mantenimiento.
 * Incluye solo campos de negocio (sin createdAt, updatedAt, version, deleted).
 *
 * @author Martin Lopez
 */
@Getter
@Setter
public class MaintenanceRecordDTOOut {

    private Long id;
    private Long vehicleId;
    private Instant maintenanceAt;
    private BigDecimal cost;
    private String performedBy;
    private String notes;
    private Integer currentKm;
}
