package com.myhotel.backendtest.repository.hr;

import com.myhotel.backendtest.entity.hr.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

/**
 * Repository Spring Data para Department.
 * @author Martin Lopez
 */
public interface DepartmentRepository extends JpaRepository<Department, BigDecimal> {
}
