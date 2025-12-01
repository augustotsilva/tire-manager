package com.prologapp.service;

import com.prologapp.domain.TirePosition;
import com.prologapp.domain.Vehicle;
import com.prologapp.repository.VehicleRepository;
import com.prologapp.service.dto.VehicleDTO;
import com.prologapp.service.dto.VehicleNoTiresDTO;
import com.prologapp.service.mapper.TirePositionsMapper;
import com.prologapp.service.mapper.VehicleMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;
    private final TirePositionsMapper tirePositionsMapper;

    public VehicleDTO getVehicleByLicensePlate(final String plate) {
        return vehicleRepository.findVehicleByLicensePlate(plate)
                .map(vehicleMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle with plate " + plate + " not found"));
    }

    public List<VehicleNoTiresDTO> getAllVehicles() {
        return vehicleMapper.toDtoNoTiresList(vehicleRepository.findAll());
    }

    public VehicleDTO createVehicle(final VehicleDTO vehicleDTO) {

        if (vehicleRepository.existsByLicensePlate((vehicleDTO.getLicensePlate()))) {
            throw new IllegalArgumentException("License plate already exists!");
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(vehicleDTO.getLicensePlate());
        vehicle.setKm(vehicleDTO.getKm());
        vehicle.setActive(vehicleDTO.isActive());
        vehicle.setBrand(vehicleDTO.getBrand());

        Set<TirePosition> positions = tirePositionsMapper.toEntitySet(vehicleDTO.getTirePositions());
        vehicle.setPositions(positions);

        if (positions != null) {
            positions.forEach(position -> position.setVehicle(vehicle));
        }

        return vehicleMapper.toDto(vehicleRepository.save(vehicle));
    }
}
