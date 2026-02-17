package com.myhotel.backendtest.dto.out.hr;

import lombok.Getter;
import lombok.Setter;

/**
 * departamento + count para segmentos por departamento.
 * @author Martin Lopez
 */
@Getter
@Setter
public class DepartamentoCountDTO {

    private String departamento;
    private Long count;
}
