package com.myhotel.backendtest.entity.hr;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Entidad Region del esquema HR.
 * Tabla: regions
 * @author Martin Lopez
 */
@Entity
@Table(name = "regions")
@Getter
@Setter
public class Region {

    @Id
    @Column(name = "REGION_ID", precision = 5, scale = 0)
    private BigDecimal regionId;

    @Column(name = "REGION_NAME", length = 25)
    private String regionName;
}
