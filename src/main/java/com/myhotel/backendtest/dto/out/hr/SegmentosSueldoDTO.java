package com.myhotel.backendtest.dto.out.hr;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO para segmentos de sueldo (punto 1).
 * Umbrales: &lt;3500, 3500-8000, &gt;=8000.
 * @author Martin Lopez
 */
@Getter
@Setter
public class SegmentosSueldoDTO {

    private Long menor3500;
    private Long entre3500y8000;
    private Long mayorIgual8000;
}
