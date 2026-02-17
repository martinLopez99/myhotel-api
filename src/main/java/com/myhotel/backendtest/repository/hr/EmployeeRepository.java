package com.myhotel.backendtest.repository.hr;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myhotel.backendtest.entity.hr.Employee;

/**
 * Repository Spring Data para Employee.
 * @author Martin Lopez
 */
public interface EmployeeRepository extends JpaRepository<Employee, BigDecimal> {
}
