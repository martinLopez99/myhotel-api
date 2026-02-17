package com.myhotel.backendtest.entity.hr;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad JobHistory del esquema HR.
 * Tabla: job_history (PK compuesta: EMPLOYEE_ID, START_DATE)
 * @author Martin Lopez
 */
@Entity
@Table(name = "job_history")
@IdClass(JobHistoryId.class)
@Getter
@Setter
public class JobHistory {

    @Id
    @Column(name = "EMPLOYEE_ID", precision = 6, scale = 0)
    private BigDecimal employeeId;

    @Id
    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "END_DATE", nullable = false)
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "JOB_ID", nullable = false)
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTMENT_ID")
    private Department department;
}
