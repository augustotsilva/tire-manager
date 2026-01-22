package com.tiremanager.service;

import com.tiremanager.domain.Tire;
import com.tiremanager.repository.TireRepository;
import com.tiremanager.service.dto.TireDTO;
import com.tiremanager.service.mapper.TireMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TireService {
    private final TireRepository tireRepository;
    private final TireMapper tireMapper;

    public List<TireDTO> getAllTires() {
        return tireMapper.toDtoList(tireRepository.findAll());
    }

    public TireDTO getTireByFireNumber(Integer fireNumber) {
        return tireRepository.findByFireNumber(fireNumber)
                .map(tireMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Tire with fire number " + fireNumber + " not found"));
    }

    public TireDTO createTire(final TireDTO tireDTO) {

        if (tireRepository.existsByFireNumber(tireDTO.getFireNumber())) {
            throw new IllegalArgumentException("Fire Number already exists!");
        }

        Tire tire = new Tire();

        tire.setFireNumber(tireDTO.getFireNumber());
        tire.setBrand(tireDTO.getBrand());
        tire.setPsiPressure(tireDTO.getPsiPressure());
        tire.setAllocated(tireDTO.isAllocated());
        tire.setPosition(null);

        return tireMapper.toDto(tireRepository.save(tire));
    }
}
