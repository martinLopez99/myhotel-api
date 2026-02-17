package com.myhotel.backendtest.controller;

import com.myhotel.backendtest.dto.RestResponse;
import com.myhotel.backendtest.dto.in.MaintenanceRecordDTOIn;
import com.myhotel.backendtest.dto.mapper.MaintenanceRecordMapper;
import com.myhotel.backendtest.dto.out.MaintenanceRecordDTOOut;
import com.myhotel.backendtest.entity.MaintenanceRecord;
import com.myhotel.backendtest.entity.Vehicle;
import com.myhotel.backendtest.exception.ErrorCode;
import com.myhotel.backendtest.exception.PreValidationException;
import com.myhotel.backendtest.service.MaintenanceRecordService;
import com.myhotel.backendtest.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para registros de mantenimiento, anidados bajo /api/vehicles/{plate}/maintenances.
 * Sin try/catch: las excepciones las maneja GlobalExceptionHandler.
 *
 * @author Martin Lopez
 */
@RestController
@RequestMapping("/api/vehicles/{plate}/maintenances")
@Validated
public class MaintenanceRecordController {

    @Autowired
    private MaintenanceRecordService maintenanceRecordService;
    
    @Autowired
    private VehicleService vehicleService;

    // ── GET ──────────────────────────────────────────────────────────────────

    @GetMapping
    public ResponseEntity<List<MaintenanceRecordDTOOut>> findByPlate(
            @PathVariable String plate,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "20") int limit) {

        List<MaintenanceRecordDTOOut> result = maintenanceRecordService
                .findByPlate(plate, offset, limit)
                .stream()
                .map(MaintenanceRecordMapper::toDTO)
                .toList();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/latest")
    public ResponseEntity<MaintenanceRecordDTOOut> getLatest(@PathVariable String plate) {
        MaintenanceRecord record = maintenanceRecordService.getLatestByPlate(plate)
                .orElseThrow(() -> new PreValidationException(
                        "No se encontró mantenimiento para patente=" + plate,
                        ErrorCode.MAINTENANCE_NOT_FOUND));

        return ResponseEntity.ok(MaintenanceRecordMapper.toDTO(record));
    }

    // ── POST ─────────────────────────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<RestResponse> create(@PathVariable String plate,
                                               @Valid @RequestBody MaintenanceRecordDTOIn dto) {
        Vehicle vehicle = vehicleService.getByPlate(plate)
                .orElseThrow(() -> new PreValidationException(
                        "No se encontró Vehicle con patente=" + plate,
                        ErrorCode.VEHICLE_NOT_FOUND));

        MaintenanceRecord entity = MaintenanceRecordMapper.toEntity(dto, vehicle);
        Long oid = maintenanceRecordService.create(entity);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RestResponse.ok(oid, "Mantenimiento registrado"));
    }

    // ── DELETE ────────────────────────────────────────────────────────────────

    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponse> delete(@PathVariable String plate,
                                               @PathVariable Long id) {
        maintenanceRecordService.softDelete(id, plate);
        return ResponseEntity.ok(RestResponse.ok(id, "Mantenimiento eliminado"));
    }
}
