package com.myhotel.backendtest.repository.hr.projection;

/**
 * Proyección para segmentos por departamento (queries 2A, 2B, 2C).
 * @author Martin Lopez
 */
public interface DepartamentoCountProjection {

    String getDepartamento();

    Long getCount();
}
