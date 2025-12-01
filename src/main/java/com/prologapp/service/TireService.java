package com.prologapp.service;

import com.prologapp.domain.Tire;
import com.prologapp.repository.TireRepository;
import com.prologapp.service.dto.TireDTO;
import com.prologapp.service.mapper.TireMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TireService {
    private final TireRepository tireRepository;
    private final TireMapper tireMapper;

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
