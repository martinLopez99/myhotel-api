package com.myhotel.backendtest.entity.hr;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Entidad Job del esquema HR.
 * Tabla: jobs
 * @author Martin Lopez
 */
@Entity
@Table(name = "jobs")
@Getter
@Setter
public class Job {

    @Id
    @Column(name = "JOB_ID", length = 10)
    private String jobId;

    @Column(name = "JOB_TITLE", nullable = false, length = 35)
    private String jobTitle;

    @Column(name = "MIN_SALARY", precision = 6, scale = 0)
    private BigDecimal minSalary;

    @Column(name = "MAX_SALARY", precision = 6, scale = 0)
    private BigDecimal maxSalary;
}
