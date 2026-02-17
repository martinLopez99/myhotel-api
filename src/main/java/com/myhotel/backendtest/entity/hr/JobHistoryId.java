package com.myhotel.backendtest.entity.hr;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Clave compuesta para JobHistory.
 * (EMPLOYEE_ID, START_DATE)
 * @author Martin Lopez
 */
@Getter
@Setter
@EqualsAndHashCode
public class JobHistoryId implements Serializable {

    private BigDecimal employeeId;
    private LocalDate startDate;
}
