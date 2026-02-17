package com.myhotel.backendtest.dto.out.hr;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO para salario promedio de deptos con +10 empleados (punto 5).
 * @author Martin Lopez
 */
@Getter
@Setter
public class AvgSalarioDeptoDTO {

    private String departamento;
    private BigDecimal salarioPromedio;
}
