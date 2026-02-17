package com.myhotel.backendtest.entity.hr;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad Country del esquema HR.
 * Tabla: countries
 * @author Martin Lopez
 */
@Entity
@Table(name = "countries")
@Getter
@Setter
public class Country {

    @Id
    @Column(name = "COUNTRY_ID", length = 2)
    private String countryId;

    @Column(name = "COUNTRY_NAME", length = 40)
    private String countryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REGION_ID")
    private Region region;
}
