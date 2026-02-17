package com.myhotel.backendtest.dto.out.hr;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para empleado(s) con mayor sueldo por departamento (punto 3).
 * @author Martin Lopez
 */
@Getter
@Setter
public class EmpleadoMaxSalarioDTO {

    private String departamento;
    private BigDecimal employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate hireDate;
    private String jobId;
    private BigDecimal salary;
    private BigDecimal commissionPct;
    private BigDecimal managerId;
    private BigDecimal departmentId;
}
