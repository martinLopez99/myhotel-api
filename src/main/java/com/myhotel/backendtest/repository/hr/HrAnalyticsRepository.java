package com.myhotel.backendtest.repository.hr;

import com.myhotel.backendtest.entity.hr.Employee;
import com.myhotel.backendtest.repository.hr.projection.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repository con queries nativas fieles al SQL definido.
 * @author Martin Lopez
 */
public interface HrAnalyticsRepository extends JpaRepository<Employee, BigDecimal> {

    // ── (1) Segmentos de sueldo (3 queries) ──────────────────────────────────

    @Query(value = "select count(*) from employees where salary < 3500", nativeQuery = true)
    Long countSalaryLessThan3500();

    @Query(value = "select count(*) from employees where salary >= 3500 and salary < 8000", nativeQuery = true)
    Long countSalaryBetween3500And8000();

    @Query(value = "select count(*) from employees where salary >= 8000", nativeQuery = true)
    Long countSalaryGreaterOrEqual8000();

    // ── (2) Segmentos por departamento (3 queries) ────────────────────────────

    @Query(value = """
        select d.department_name as departamento, count(*) as count
        from departments d join employees e on d.department_id = e.department_id
        where e.salary < 3500 group by d.department_name
        """, nativeQuery = true)
    List<DepartamentoCountProjection> findSegmentoPorDepartamentoMenor3500();

    @Query(value = """
        select d.department_name as departamento, count(*) as count
        from departments d join employees e on d.department_id = e.department_id
        where e.salary >= 3500 and e.salary < 8000 group by d.department_name
        """, nativeQuery = true)
    List<DepartamentoCountProjection> findSegmentoPorDepartamentoEntre3500y8000();

    @Query(value = """
        select d.department_name as departamento, count(*) as count
        from departments d join employees e on d.department_id = e.department_id
        where e.salary >= 8000 group by d.department_name
        """, nativeQuery = true)
    List<DepartamentoCountProjection> findSegmentoPorDepartamentoMayorIgual8000();

    // ── (3) Empleado(s) con mayor sueldo de cada departamento ─────────────────

    @Query(value = """
        select d.department_name as departmentName, e.employee_id as employeeId,
               e.first_name as firstName, e.last_name as lastName, e.email as email,
               e.phone_number as phoneNumber, e.hire_date as hireDate, e.job_id as jobId,
               e.salary as salary, e.commission_pct as commissionPct, e.manager_id as managerId,
               e.department_id as departmentId
        from departments d
        join employees e on d.department_id = e.department_id
        where e.salary = (
          select max(e2.salary) from employees e2 where e.department_id = e2.department_id
        )
        """, nativeQuery = true)
    List<MaxSalaryEmployeeProjection> findEmpleadosMaxSalarioPorDepartamento();

    // ── (4) Gerentes contratados hace más de 15 años (según job_id) ───────────

    @Query(value = """
        select employee_id as employeeId, first_name as firstName, last_name as lastName,
               email, phone_number as phoneNumber, hire_date as hireDate, job_id as jobId,
               salary, commission_pct as commissionPct, manager_id as managerId,
               department_id as departmentId
        from employees
        where hire_date < DATE_SUB(CURRENT_DATE(), INTERVAL 15 YEAR)
          and (job_id like '%_MAN' or job_id like '%_MGR' or job_id like '%_PRES' or job_id like '%_VP')
        """, nativeQuery = true)
    List<Gerente15AniosProjection> findGerentesContratadosHaceMasDe15Anios();

    // ── (5) Salario promedio de deptos con más de 10 empleados ────────────────

    @Query(value = """
        select d.department_name as departmentName, avg(e.salary) as salarioPromedio
        from employees e
        join departments d on e.department_id = d.department_id
        group by d.department_name
        having count(*) > 10
        """, nativeQuery = true)
    List<AvgSalaryDepartamentoProjection> findAvgSalarioDeptosMasDe10Empleados();

    // ── (6) Métricas por país ────────────────────────────────────────────────

    @Query(value = """
        select c.country_name as pais,
               count(*) as totalEmpleados,
               max(e.salary) as salarioMaximo,
               min(e.salary) as salarioMinimo,
               avg(e.salary) as salarioPromedio,
               avg(TIMESTAMPDIFF(YEAR, e.hire_date, curdate())) as promedioAntiguedad
        from employees e
        join departments d on e.department_id = d.department_id
        join locations l on l.location_id = d.location_id
        join countries c on c.country_id = l.country_id
        group by c.country_name
        """, nativeQuery = true)
    List<MetricasPaisProjection> findMetricasPorPais();
}
