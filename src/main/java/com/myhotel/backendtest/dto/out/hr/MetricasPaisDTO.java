package com.myhotel.backendtest.dto.out.hr;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO para métricas por país (punto 6).
 * @author Martin Lopez
 */
@Getter
@Setter
public class MetricasPaisDTO {

    private String pais;
    private Long totalEmpleados;
    private BigDecimal salarioMaximo;
    private BigDecimal salarioMinimo;
    private BigDecimal salarioPromedio;
    private Double promedioAntiguedad;
}
