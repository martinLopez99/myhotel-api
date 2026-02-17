package com.myhotel.backendtest.service;

import com.myhotel.backendtest.dto.out.hr.*;
import com.myhotel.backendtest.exception.ServiceException;

import java.util.List;

/**
 * Servicio de analytics HR.
 * Expone los 6 puntos de forma fiel a las queries SQL definidas.
 * @author Martin Lopez
 */
public interface HrAnalyticsService {

    /**
     * (1) Segmentos de sueldo: menor3500, entre3500y8000, mayorIgual8000.
     */
    SegmentosSueldoDTO getSegmentosSueldo() throws ServiceException;

    /**
     * (2) Segmentos por departamento: 3 listas (menor3500, entre3500y8000, mayorIgual8000).
     */
    SegmentosPorDepartamentoDTO getSegmentosPorDepartamento() throws ServiceException;

    /**
     * (3) Empleado(s) con mayor sueldo de cada departamento.
     */
    List<EmpleadoMaxSalarioDTO> getEmpleadosMaxSalarioPorDepartamento() throws ServiceException;

    /**
     * (4) Gerentes contratados hace más de 15 años (job_id: _MAN, _MGR, _PRES, _VP).
     */
    List<EmpleadoBasicoDTO> getGerentesContratadosHaceMasDe15Anios() throws ServiceException;

    /**
     * (5) Salario promedio de departamentos con más de 10 empleados.
     */
    List<AvgSalarioDeptoDTO> getAvgSalarioDeptosMasDe10Empleados() throws ServiceException;

    /**
     * (6) Métricas por país.
     */
    List<MetricasPaisDTO> getMetricasPorPais() throws ServiceException;
}
