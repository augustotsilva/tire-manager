package com.prologapp.service;

import com.prologapp.domain.Tire;
import com.prologapp.domain.TirePosition;
import com.prologapp.repository.TirePositionRepository;
import com.prologapp.repository.TireRepository;
import com.prologapp.service.dto.TireAllocationByPlateDTO;
import com.prologapp.service.dto.TireDeallocationByPlateDTO;
import com.prologapp.service.dto.TirePositionDTO;
import com.prologapp.service.mapper.TirePositionsMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TirePositionService {

    private final TirePositionRepository tirePositionRepository;
    private final TireRepository tireRepository;
    private final TirePositionsMapper tirePositionsMapper;

    @Transactional
    public TirePositionDTO allocateTire(TireAllocationByPlateDTO dto) {

        TirePosition position = tirePositionRepository
                .findByVehicleLicensePlateAndIdentifier(dto.getLicensePlate(), dto.getTirePositionIdentifier())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Position '" + dto.getTirePositionIdentifier() +
                                "' not found for vehicle with plate " + dto.getLicensePlate()
                ));

        Tire tire = tireRepository.findByFireNumber(dto.getFireNumber())
                .orElseThrow(() -> new EntityNotFoundException("Tire with fire number " + dto.getFireNumber() + " not found."));

        if (position.isHasTire()) {
            throw new IllegalArgumentException("Position is already occupied by a tire.");
        }
        if (tire.isAllocated()) {
            throw new IllegalArgumentException("Tire is already allocated to a position.");
        }

        tire.setAllocated(true);
        tire.setPosition(position);
        tireRepository.save(tire);

        position.setTire(tire);
        position.setHasTire(true);

        TirePosition savedPosition = tirePositionRepository.save(position);

        return tirePositionsMapper.toDto(savedPosition);
    }

    @Transactional
    public TirePositionDTO deallocateTire(TireDeallocationByPlateDTO dto) {

        TirePosition position = tirePositionRepository
                .findByVehicleLicensePlateAndIdentifier(dto.getLicensePlate(), dto.getTirePositionIdentifier())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Position '" + dto.getTirePositionIdentifier() +
                                "' not found for vehicle with plate " + dto.getLicensePlate()
                ));

        if (!position.isHasTire() || position.getTire() == null) {
            throw new IllegalArgumentException("Position is already empty. Cannot deallocate.");
        }

        Tire tire = position.getTire();

        tire.setAllocated(false);
        tire.setPosition(null);
        tireRepository.save(tire);

        position.setTire(null);
        position.setHasTire(false);

        TirePosition savedPosition = tirePositionRepository.save(position);

        return tirePositionsMapper.toDto(savedPosition);
    }
}