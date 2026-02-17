package com.myhotel.backendtest.service.impl;

import com.myhotel.backendtest.dto.out.hr.*;
import com.myhotel.backendtest.exception.ErrorCode;
import com.myhotel.backendtest.exception.ServiceException;
import com.myhotel.backendtest.repository.hr.projection.*;
import com.myhotel.backendtest.repository.hr.HrAnalyticsRepository;
import com.myhotel.backendtest.service.HrAnalyticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de analytics HR.
 * @author Martin Lopez
 */
@Service
@Transactional(readOnly = true)
public class HrAnalyticsServiceImpl implements HrAnalyticsService {

    private static final Logger log = LoggerFactory.getLogger(HrAnalyticsServiceImpl.class);

    @Autowired
    private HrAnalyticsRepository hrAnalyticsRepository;

    @Override
    public SegmentosSueldoDTO getSegmentosSueldo() {
        try {
            SegmentosSueldoDTO dto = new SegmentosSueldoDTO();
            dto.setMenor3500(hrAnalyticsRepository.countSalaryLessThan3500());
            dto.setEntre3500y8000(hrAnalyticsRepository.countSalaryBetween3500And8000());
            dto.setMayorIgual8000(hrAnalyticsRepository.countSalaryGreaterOrEqual8000());
            return dto;
        } catch (Exception e) {
            log.error("Error al obtener segmentos de sueldo", e);
            throw new ServiceException("Error al obtener segmentos de sueldo",
                    ErrorCode.UNEXPECTED_ERROR, e);
        }
    }

    @Override
    public SegmentosPorDepartamentoDTO getSegmentosPorDepartamento() {
        try {
            SegmentosPorDepartamentoDTO dto = new SegmentosPorDepartamentoDTO();
            dto.setMenor3500(toDepartamentoCountDTO(hrAnalyticsRepository.findSegmentoPorDepartamentoMenor3500()));
            dto.setEntre3500y8000(toDepartamentoCountDTO(hrAnalyticsRepository.findSegmentoPorDepartamentoEntre3500y8000()));
            dto.setMayorIgual8000(toDepartamentoCountDTO(hrAnalyticsRepository.findSegmentoPorDepartamentoMayorIgual8000()));
            return dto;
        } catch (Exception e) {
            log.error("Error al obtener segmentos por departamento", e);
            throw new ServiceException("Error al obtener segmentos por departamento",
                    ErrorCode.UNEXPECTED_ERROR, e);
        }
    }

    @Override
    public List<EmpleadoMaxSalarioDTO> getEmpleadosMaxSalarioPorDepartamento() {
        try {
            return hrAnalyticsRepository.findEmpleadosMaxSalarioPorDepartamento().stream()
                    .map(this::toEmpleadoMaxSalarioDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error al obtener empleados con max salario por departamento", e);
            throw new ServiceException("Error al obtener empleados con max salario por departamento",
                    ErrorCode.UNEXPECTED_ERROR, e);
        }
    }

    @Override
    public List<EmpleadoBasicoDTO> getGerentesContratadosHaceMasDe15Anios() {
        try {
            return hrAnalyticsRepository.findGerentesContratadosHaceMasDe15Anios().stream()
                    .map(this::toEmpleadoBasicoDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error al obtener gerentes contratados hace 15+ años", e);
            throw new ServiceException("Error al obtener gerentes contratados hace 15+ años",
                    ErrorCode.UNEXPECTED_ERROR, e);
        }
    }

    @Override
    public List<AvgSalarioDeptoDTO> getAvgSalarioDeptosMasDe10Empleados() {
        try {
            return hrAnalyticsRepository.findAvgSalarioDeptosMasDe10Empleados().stream()
                    .map(p -> {
                        AvgSalarioDeptoDTO dto = new AvgSalarioDeptoDTO();
                        dto.setDepartamento(p.getDepartmentName());
                        dto.setSalarioPromedio(p.getSalarioPromedio());
                        return dto;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error al obtener avg salario deptos +10 empleados", e);
            throw new ServiceException("Error al obtener salario promedio de departamentos",
                    ErrorCode.UNEXPECTED_ERROR, e);
        }
    }

    @Override
    public List<MetricasPaisDTO> getMetricasPorPais() {
        try {
            return hrAnalyticsRepository.findMetricasPorPais().stream()
                    .map(this::toMetricasPaisDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error al obtener métricas por país", e);
            throw new ServiceException("Error al obtener métricas por país",
                    ErrorCode.UNEXPECTED_ERROR, e);
        }
    }

    private List<DepartamentoCountDTO> toDepartamentoCountDTO(List<DepartamentoCountProjection> list) {
        return list.stream()
                .map(p -> {
                    DepartamentoCountDTO dto = new DepartamentoCountDTO();
                    dto.setDepartamento(p.getDepartamento());
                    dto.setCount(p.getCount());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private EmpleadoMaxSalarioDTO toEmpleadoMaxSalarioDTO(MaxSalaryEmployeeProjection p) {
        EmpleadoMaxSalarioDTO dto = new EmpleadoMaxSalarioDTO();
        dto.setDepartamento(p.getDepartmentName());
        dto.setEmployeeId(p.getEmployeeId());
        dto.setFirstName(p.getFirstName());
        dto.setLastName(p.getLastName());
        dto.setEmail(p.getEmail());
        dto.setPhoneNumber(p.getPhoneNumber());
        dto.setHireDate(p.getHireDate());
        dto.setJobId(p.getJobId());
        dto.setSalary(p.getSalary());
        dto.setCommissionPct(p.getCommissionPct());
        dto.setManagerId(p.getManagerId());
        dto.setDepartmentId(p.getDepartmentId());
        return dto;
    }

    private EmpleadoBasicoDTO toEmpleadoBasicoDTO(Gerente15AniosProjection p) {
        EmpleadoBasicoDTO dto = new EmpleadoBasicoDTO();
        dto.setEmployeeId(p.getEmployeeId());
        dto.setFirstName(p.getFirstName());
        dto.setLastName(p.getLastName());
        dto.setEmail(p.getEmail());
        dto.setPhoneNumber(p.getPhoneNumber());
        dto.setHireDate(p.getHireDate());
        dto.setJobId(p.getJobId());
        dto.setSalary(p.getSalary());
        dto.setCommissionPct(p.getCommissionPct());
        dto.setManagerId(p.getManagerId());
        dto.setDepartmentId(p.getDepartmentId());
        return dto;
    }

    private MetricasPaisDTO toMetricasPaisDTO(MetricasPaisProjection p) {
        MetricasPaisDTO dto = new MetricasPaisDTO();
        dto.setPais(p.getPais());
        dto.setTotalEmpleados(p.getTotalEmpleados());
        dto.setSalarioMaximo(p.getSalarioMaximo());
        dto.setSalarioMinimo(p.getSalarioMinimo());
        dto.setSalarioPromedio(p.getSalarioPromedio());
        dto.setPromedioAntiguedad(p.getPromedioAntiguedad());
        return dto;
    }
}
