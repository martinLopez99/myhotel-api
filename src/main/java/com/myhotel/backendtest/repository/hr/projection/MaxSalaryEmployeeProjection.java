package com.myhotel.backendtest.repository.hr.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Proyección para empleado(s) con mayor sueldo por departamento (query 3).
 * @author Martin Lopez
 */
public interface MaxSalaryEmployeeProjection {

    String getDepartmentName();

    BigDecimal getEmployeeId();

    String getFirstName();

    String getLastName();

    String getEmail();

    String getPhoneNumber();

    LocalDate getHireDate();

    String getJobId();

    BigDecimal getSalary();

    BigDecimal getCommissionPct();

    BigDecimal getManagerId();

    BigDecimal getDepartmentId();
}
