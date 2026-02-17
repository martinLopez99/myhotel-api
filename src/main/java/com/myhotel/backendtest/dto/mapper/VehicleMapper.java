package com.myhotel.backendtest.dto.mapper;

import com.myhotel.backendtest.dto.in.VehicleDTOIn;
import com.myhotel.backendtest.dto.out.VehicleDTOOut;
import com.myhotel.backendtest.entity.Car;
import com.myhotel.backendtest.entity.Truck;
import com.myhotel.backendtest.entity.Vehicle;
import com.myhotel.backendtest.exception.ErrorCode;
import com.myhotel.backendtest.exception.PreValidationException;

/**
 * Mapper estático para conversiones Vehicle ↔ DTO.
 *
 * @author Martin Lopez
 */
public final class VehicleMapper {

    private VehicleMapper() { }

    // ── DTO In → Entity ─────────────────────────────────────────────────────

    /**
     * Crea una nueva entidad (Car o Truck) a partir del DTO de entrada.
     */
    public static Vehicle toEntity(VehicleDTOIn dto) {
        return switch (dto.getType().toUpperCase()) {
            case "CAR" -> toCar(dto);
            case "TRUCK" -> toTruck(dto);
            default -> throw new PreValidationException(
                    "Tipo de vehículo inválido: " + dto.getType() + ". Valores permitidos: Car, Truck",
                    ErrorCode.VEHICLE_INVALID_TYPE);
        };
    }

    /**
     * Actualiza los campos de una entidad existente con los datos del DTO.
     * No cambia el subtipo (Car/Truck); solo actualiza campos comunes y específicos.
     */
    public static void updateEntity(Vehicle entity, VehicleDTOIn dto) {
        entity.setBrand(dto.getBrand());
        entity.setModel(dto.getModel());
        entity.setYear(dto.getYear());
        entity.setPlate(dto.getPlate());
        entity.setMileageKm(dto.getMileageKm());
        entity.setEngineDisplacementCc(dto.getEngineDisplacementCc());

        if (entity instanceof Car car) {
            car.setCarType(dto.getCarType());
            car.setNumberOfDoors(dto.getNumberOfDoors());
            car.setPassengerCapacity(dto.getPassengerCapacity());
            car.setTrunkCapacityLiters(dto.getTrunkCapacityLiters());
        } else if (entity instanceof Truck truck) {
            truck.setTruckType(dto.getTruckType());
            truck.setLoadCapacityTons(dto.getLoadCapacityTons());
            truck.setAxles(dto.getAxles());
        }
    }

    // ── Entity → DTO Out ────────────────────────────────────────────────────

    public static VehicleDTOOut toDTO(Vehicle entity) {
        VehicleDTOOut dto = new VehicleDTOOut();
        dto.setId(entity.getId());
        dto.setBrand(entity.getBrand());
        dto.setModel(entity.getModel());
        dto.setYear(entity.getYear());
        dto.setPlate(entity.getPlate());
        dto.setMileageKm(entity.getMileageKm());
        dto.setEngineDisplacementCc(entity.getEngineDisplacementCc());
        dto.setDeleted(entity.isDeleted());

        if (entity instanceof Car car) {
            dto.setType("Car");
            dto.setCarType(car.getCarType());
            dto.setNumberOfDoors(car.getNumberOfDoors());
            dto.setPassengerCapacity(car.getPassengerCapacity());
            dto.setTrunkCapacityLiters(car.getTrunkCapacityLiters());
        } else if (entity instanceof Truck truck) {
            dto.setType("Truck");
            dto.setTruckType(truck.getTruckType());
            dto.setLoadCapacityTons(truck.getLoadCapacityTons());
            dto.setAxles(truck.getAxles());
        }

        return dto;
    }

    // ── Helpers privados ────────────────────────────────────────────────────

    private static Car toCar(VehicleDTOIn dto) {
        Car car = new Car();
        setCommonFields(car, dto);
        car.setCarType(dto.getCarType());
        car.setNumberOfDoors(dto.getNumberOfDoors());
        car.setPassengerCapacity(dto.getPassengerCapacity());
        car.setTrunkCapacityLiters(dto.getTrunkCapacityLiters());
        return car;
    }

    private static Truck toTruck(VehicleDTOIn dto) {
        Truck truck = new Truck();
        setCommonFields(truck, dto);
        truck.setTruckType(dto.getTruckType());
        truck.setLoadCapacityTons(dto.getLoadCapacityTons());
        truck.setAxles(dto.getAxles());
        return truck;
    }

    private static void setCommonFields(Vehicle entity, VehicleDTOIn dto) {
        entity.setBrand(dto.getBrand());
        entity.setModel(dto.getModel());
        entity.setYear(dto.getYear());
        entity.setPlate(dto.getPlate());
        entity.setMileageKm(dto.getMileageKm());
        entity.setEngineDisplacementCc(dto.getEngineDisplacementCc());
    }
}
