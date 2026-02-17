package com.myhotel.backendtest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Automóvil: entidad concreta que extiende Vehicle.
 * Atributos específicos del automóvil (todos nullable por estrategia SINGLE_TABLE).
 *
 * @author Martin Lopez
 */
@Entity
@DiscriminatorValue("Car")
@Getter
@Setter
public class Car extends Vehicle {

    @Column(name = "car_type", length = 30)
    private String carType;

    @Column(name = "number_of_doors")
    private Integer numberOfDoors;

    @Column(name = "passenger_capacity")
    private Integer passengerCapacity;

    @Column(name = "trunk_capacity_liters")
    private Integer trunkCapacityLiters;
}
