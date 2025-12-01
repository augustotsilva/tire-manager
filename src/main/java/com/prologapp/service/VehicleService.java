package com.prologapp.service;

import com.prologapp.repository.VehicleRepository;
import com.prologapp.service.dto.VehicleDTO;
import com.prologapp.service.dto.VehicleNoTiresDTO;
import com.prologapp.service.mapper.VehicleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleDTO getVehicleByLicensePlate(final String plate) {
        return VehicleMapper.toDto(vehicleRepository.findVehicleByLicensePlate(plate));
    }

    public List<VehicleNoTiresDTO> getAllVehicles() {
        return VehicleMapper.toDtoNoTiresList(vehicleRepository.findAll());
    }
}
