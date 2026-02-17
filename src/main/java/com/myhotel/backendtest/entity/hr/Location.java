package com.myhotel.backendtest.entity.hr;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Entidad Location del esquema HR.
 * Tabla: locations
 * @author Martin Lopez
 */
@Entity
@Table(name = "locations")
@Getter
@Setter
public class Location {

    @Id
    @Column(name = "LOCATION_ID", precision = 4, scale = 0)
    private BigDecimal locationId;

    @Column(name = "STREET_ADDRESS", length = 40)
    private String streetAddress;

    @Column(name = "POSTAL_CODE", length = 12)
    private String postalCode;

    @Column(name = "CITY", nullable = false, length = 30)
    private String city;

    @Column(name = "STATE_PROVINCE", length = 25)
    private String stateProvince;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUNTRY_ID")
    private Country country;
}
