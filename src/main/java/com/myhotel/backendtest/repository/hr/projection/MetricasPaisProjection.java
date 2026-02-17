package com.myhotel.backendtest.repository.hr.projection;

import java.math.BigDecimal;

/**
 * Proyección para métricas por país (query 6).
 * @author Martin Lopez
 */
public interface MetricasPaisProjection {

    String getPais();

    Long getTotalEmpleados();

    BigDecimal getSalarioMaximo();

    BigDecimal getSalarioMinimo();

    BigDecimal getSalarioPromedio();

    Double getPromedioAntiguedad();
}
