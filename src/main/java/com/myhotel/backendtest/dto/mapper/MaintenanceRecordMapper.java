package com.myhotel.backendtest.dto.mapper;

import com.myhotel.backendtest.dto.in.MaintenanceRecordDTOIn;
import com.myhotel.backendtest.dto.out.MaintenanceRecordDTOOut;
import com.myhotel.backendtest.entity.MaintenanceRecord;
import com.myhotel.backendtest.entity.Vehicle;

/**
 * Mapper estático para conversiones MaintenanceRecord ↔ DTO.
 *
 * @author Martin Lopez
 */
public final class MaintenanceRecordMapper {

    private MaintenanceRecordMapper() { }

    // ── DTO In → Entity ─────────────────────────────────────────────────────

    /**
     * Crea una nueva entidad a partir del DTO y la referencia al vehículo.
     */
    public static MaintenanceRecord toEntity(MaintenanceRecordDTOIn dto, Vehicle vehicle) {
        MaintenanceRecord entity = new MaintenanceRecord();
        entity.setVehicle(vehicle);
        entity.setMaintenanceAt(dto.getMaintenanceAt());
        entity.setCost(dto.getCost());
        entity.setPerformedBy(dto.getPerformedBy());
        entity.setNotes(dto.getNotes());
        entity.setCurrentKm(dto.getCurrentKm());
        return entity;
    }

    /**
     * Actualiza los campos de una entidad existente con los datos del DTO.
     */
    public static void updateEntity(MaintenanceRecord entity, MaintenanceRecordDTOIn dto) {
        entity.setMaintenanceAt(dto.getMaintenanceAt());
        entity.setCost(dto.getCost());
        entity.setPerformedBy(dto.getPerformedBy());
        entity.setNotes(dto.getNotes());
        entity.setCurrentKm(dto.getCurrentKm());
    }

    // ── Entity → DTO Out ────────────────────────────────────────────────────

    public static MaintenanceRecordDTOOut toDTO(MaintenanceRecord entity) {
        MaintenanceRecordDTOOut dto = new MaintenanceRecordDTOOut();
        dto.setId(entity.getId());
        dto.setVehicleId(entity.getVehicle() != null ? entity.getVehicle().getId() : null);
        dto.setMaintenanceAt(entity.getMaintenanceAt());
        dto.setCost(entity.getCost());
        dto.setPerformedBy(entity.getPerformedBy());
        dto.setNotes(entity.getNotes());
        dto.setCurrentKm(entity.getCurrentKm());
        return dto;
    }
}
