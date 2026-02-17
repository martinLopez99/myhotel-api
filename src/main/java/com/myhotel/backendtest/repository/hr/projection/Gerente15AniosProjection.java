package com.myhotel.backendtest.repository.hr.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Proyección para gerentes contratados hace más de 15 años (query 4).
 * @author Martin Lopez
 */
public interface Gerente15AniosProjection {

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
