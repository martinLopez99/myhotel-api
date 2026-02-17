package com.myhotel.backendtest.repository.hr.projection;

import java.math.BigDecimal;

/**
 * Proyección para salario promedio de deptos con más de 10 empleados (query 5).
 * @author Martin Lopez
 */
public interface AvgSalaryDepartamentoProjection {

    String getDepartmentName();

    BigDecimal getSalarioPromedio();
}
