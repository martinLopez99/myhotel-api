package com.myhotel.backendtest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Camión: entidad concreta que extiende Vehicle.
 * Atributos específicos del camión (todos nullable por estrategia SINGLE_TABLE).
 *
 * @author Martin Lopez
 */
@Entity
@DiscriminatorValue("Truck")
@Getter
@Setter
public class Truck extends Vehicle {

    @Column(name = "truck_type", length = 30)
    private String truckType;

    @Column(name = "load_capacity_tons")
    private Integer loadCapacityTons;

    @Column
    private Integer axles;
}
