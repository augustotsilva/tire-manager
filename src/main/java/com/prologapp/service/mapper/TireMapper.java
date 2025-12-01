package com.prologapp.service.mapper;

import com.prologapp.domain.Tire;
import com.prologapp.service.dto.TireDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TireMapper {

    public TireDTO toDto(Tire tire) {
        if (tire == null) {
            return null;
        }

        return new TireDTO(
                tire.getId(),
                tire.getFireNumber(),
                tire.getBrand(),
                tire.getPsiPressure(),
                tire.isAllocated()
        );
    }

    public List<TireDTO> toDtoList(List<Tire> tires) {
        if (tires == null) {
            return List.of();
        }

        return tires.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Set<TireDTO> toDtoSet(Set<Tire> tires) {
        if (tires == null) {
            return Set.of();
        }

        return tires.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }
}