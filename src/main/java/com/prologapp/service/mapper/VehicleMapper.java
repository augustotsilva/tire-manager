package com.prologapp.service.mapper;

import com.prologapp.domain.Vehicle;
import com.prologapp.service.dto.VehicleDTO;
import com.prologapp.service.dto.VehicleNoTiresDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public final class VehicleMapper {

    public static VehicleNoTiresDTO toDtoNoTires(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }

        return new VehicleNoTiresDTO(
                vehicle.getId(),
                vehicle.getLicensePlate(),
                vehicle.getBrand(),
                vehicle.getKm(),
                vehicle.isActive()
        );
    }


    public static List<VehicleNoTiresDTO> toDtoNoTiresList(List<Vehicle> vehicles) {
        if (vehicles == null) {
            return List.of();
        }

        return vehicles.stream()
                .map(VehicleMapper::toDtoNoTires)
                .collect(Collectors.toList());
    }

    public static VehicleDTO toDto(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }

        return new VehicleDTO(
                vehicle.getId(),
                vehicle.getLicensePlate(),
                vehicle.getBrand(),
                vehicle.getKm(),
                vehicle.isActive(),
                TirePositionsMapper.toDtoSet(vehicle.getPositions())
        );
    }


    public static List<VehicleDTO> toDtoList(List<Vehicle> vehicles) {
        if (vehicles == null) {
            return List.of();
        }

        return vehicles.stream()
                .map(VehicleMapper::toDto)
                .collect(Collectors.toList());
    }
}
