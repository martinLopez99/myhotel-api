package com.myhotel.backendtest.dto.out.hr;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * DTO para segmentos de sueldo por departamento (punto 2).
 * @author Martin Lopez
 */
@Getter
@Setter
public class SegmentosPorDepartamentoDTO {

    private List<DepartamentoCountDTO> menor3500;
    private List<DepartamentoCountDTO> entre3500y8000;
    private List<DepartamentoCountDTO> mayorIgual8000;
}
