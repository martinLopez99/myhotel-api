package com.myhotel.backendtest.controller;

import com.myhotel.backendtest.dto.out.hr.*;
import com.myhotel.backendtest.service.HrAnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para analytics HR.
 * Rutas versionadas bajo /api/v1/analytics.
 * @author Martin Lopez
 */
@RestController
@RequestMapping("/api/v1/analytics")
@Validated
public class HrAnalyticsController {

    @Autowired
    private HrAnalyticsService hrAnalyticsService;

    /**
     * (1) Segmentos de sueldo.
     */
    @GetMapping("/segmentos-sueldo")
    public ResponseEntity<SegmentosSueldoDTO> getSegmentosSueldo() {
        return ResponseEntity.ok(hrAnalyticsService.getSegmentosSueldo());
    }

    /**
     * (2) Segmentos por departamento.
     */
    @GetMapping("/segmentos-por-departamento")
    public ResponseEntity<SegmentosPorDepartamentoDTO> getSegmentosPorDepartamento() {
        return ResponseEntity.ok(hrAnalyticsService.getSegmentosPorDepartamento());
    }

    /**
     * (3) Empleado(s) con mayor sueldo de cada departamento.
     */
    @GetMapping("/empleados-max-salario-por-departamento")
    public ResponseEntity<List<EmpleadoMaxSalarioDTO>> getEmpleadosMaxSalarioPorDepartamento() {
        return ResponseEntity.ok(hrAnalyticsService.getEmpleadosMaxSalarioPorDepartamento());
    }

    /**
     * (4) Gerentes contratados hace más de 15 años.
     */
    @GetMapping("/gerentes-15-anios")
    public ResponseEntity<List<EmpleadoBasicoDTO>> getGerentesContratadosHaceMasDe15Anios() {
        return ResponseEntity.ok(hrAnalyticsService.getGerentesContratadosHaceMasDe15Anios());
    }

    /**
     * (5) Salario promedio de deptos con más de 10 empleados.
     */
    @GetMapping("/avg-salario-deptos-10-plus")
    public ResponseEntity<List<AvgSalarioDeptoDTO>> getAvgSalarioDeptosMasDe10Empleados() {
        return ResponseEntity.ok(hrAnalyticsService.getAvgSalarioDeptosMasDe10Empleados());
    }

    /**
     * (6) Métricas por país.
     */
    @GetMapping("/metricas-por-pais")
    public ResponseEntity<List<MetricasPaisDTO>> getMetricasPorPais() {
        return ResponseEntity.ok(hrAnalyticsService.getMetricasPorPais());
    }
}
