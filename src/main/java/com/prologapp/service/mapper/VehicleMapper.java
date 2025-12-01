package com.prologapp.service.mapper;

import com.prologapp.domain.Vehicle;
import com.prologapp.service.dto.VehicleDTO;
import com.prologapp.service.dto.VehicleNoTiresDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VehicleMapper {

    private final TirePositionsMapper tirePositionsMapper;

    public VehicleNoTiresDTO toDtoNoTires(Vehicle vehicle) {
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

    public List<VehicleNoTiresDTO> toDtoNoTiresList(List<Vehicle> vehicles) {
        if (vehicles == null) {
            return List.of();
        }

        return vehicles.stream()
                .map(this::toDtoNoTires)
                .collect(Collectors.toList());
    }

    public VehicleDTO toDto(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }

        return new VehicleDTO(
                vehicle.getId(),
                vehicle.getLicensePlate(),
                vehicle.getBrand(),
                vehicle.getKm(),
                vehicle.isActive(),
                tirePositionsMapper.toDtoSet(vehicle.getPositions())
        );
    }

    public List<VehicleDTO> toDtoList(List<Vehicle> vehicles) {
        if (vehicles == null) {
            return List.of();
        }

        return vehicles.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}