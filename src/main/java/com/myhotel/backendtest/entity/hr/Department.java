package com.myhotel.backendtest.entity.hr;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad Department del esquema HR.
 * Tabla: departments
 * @author Martin Lopez
 */
@Entity
@Table(name = "departments")
@Getter
@Setter
public class Department {

    @Id
    @Column(name = "DEPARTMENT_ID", precision = 4, scale = 0)
    private BigDecimal departmentId;

    @Column(name = "DEPARTMENT_NAME", nullable = false, length = 30)
    private String departmentName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANAGER_ID")
    private Employee manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION_ID")
    private Location location;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = {})
    private List<Employee> employees = new ArrayList<>();
}
