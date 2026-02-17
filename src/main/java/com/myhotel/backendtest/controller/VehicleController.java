package com.myhotel.backendtest.controller;

import com.myhotel.backendtest.dao.criteria.VehicleSearchCriteria;
import com.myhotel.backendtest.dto.RestResponse;
import com.myhotel.backendtest.dto.in.VehicleDTOIn;
import com.myhotel.backendtest.dto.mapper.VehicleMapper;
import com.myhotel.backendtest.dto.out.VehicleDTOOut;
import com.myhotel.backendtest.entity.Vehicle;
import com.myhotel.backendtest.exception.ErrorCode;
import com.myhotel.backendtest.exception.PreValidationException;
import com.myhotel.backendtest.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para vehículos.
 * Sin try/catch: las excepciones las maneja GlobalExceptionHandler.
 *
 * @author Martin Lopez
 */
@RestController
@RequestMapping("/api/vehicles")
@Validated
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    // ── GET ──────────────────────────────────────────────────────────────────

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTOOut> getById(@PathVariable Long id) {
        // El service lanza ServiceException si no existe físicamente, así que el Optional siempre tendrá valor
        Vehicle vehicle = vehicleService.getById(id).get();
        return ResponseEntity.ok(VehicleMapper.toDTO(vehicle));
    }

    @GetMapping(params = "plate")
    public ResponseEntity<VehicleDTOOut> getByPlate(@RequestParam String plate) {
        Vehicle vehicle = vehicleService.getByPlate(plate)
                .orElseThrow(() -> new PreValidationException(
                        "No se encontró Vehicle con plate=" + plate,
                        ErrorCode.VEHICLE_NOT_FOUND));

        return ResponseEntity.ok(VehicleMapper.toDTO(vehicle));
    }

    @GetMapping
    public ResponseEntity<List<VehicleDTOOut>> findAll(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "20") int limit) {

        List<VehicleDTOOut> result = vehicleService.findAll(offset, limit)
                .stream()
                .map(VehicleMapper::toDTO)
                .toList();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/search")
    public ResponseEntity<List<VehicleDTOOut>> search(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Integer yearFrom,
            @RequestParam(required = false) Integer yearTo,
            @RequestParam(required = false) String dtype,
            @RequestParam(required = false) String plateLike,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "20") int limit) {

        VehicleSearchCriteria criteria = new VehicleSearchCriteria();
        criteria.setBrand(brand);
        criteria.setModel(model);
        criteria.setYearFrom(yearFrom);
        criteria.setYearTo(yearTo);
        criteria.setDtype(dtype);
        criteria.setPlateLike(plateLike);

        List<VehicleDTOOut> result = vehicleService.search(criteria, offset, limit)
                .stream()
                .map(VehicleMapper::toDTO)
                .toList();

        return ResponseEntity.ok(result);
    }

    // ── POST ─────────────────────────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<RestResponse> create(@Valid @RequestBody VehicleDTOIn dto) {
        Vehicle entity = VehicleMapper.toEntity(dto);
        Long oid = vehicleService.create(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(RestResponse.ok(oid, "Vehículo creado"));
    }

    // ── PUT ──────────────────────────────────────────────────────────────────

    @PutMapping("/{id}")
    public ResponseEntity<RestResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody VehicleDTOIn dto) {
        Vehicle existing = vehicleService.getById(id)
                .orElseThrow(() -> new PreValidationException(
                        "No se encontró Vehicle con id=" + id,
                        ErrorCode.VEHICLE_NOT_FOUND));

        VehicleMapper.updateEntity(existing, dto);
        vehicleService.update(existing);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(RestResponse.ok(id, "Vehículo actualizado"));
    }

    // ── DELETE ────────────────────────────────────────────────────────────────

    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponse> delete(@PathVariable Long id) {
        vehicleService.softDelete(id);
        return ResponseEntity.ok(RestResponse.ok(id, "Vehículo eliminado"));
    }
}
