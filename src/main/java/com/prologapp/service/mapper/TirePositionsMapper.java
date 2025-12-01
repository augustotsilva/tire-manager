package com.prologapp.service.mapper;

import com.prologapp.domain.Tire;
import com.prologapp.domain.TirePosition;
import com.prologapp.service.dto.TireDTO;
import com.prologapp.service.dto.TirePositionDTO;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public final class TirePositionsMapper {

    private TirePositionsMapper() {
    }

    private static TireDTO toTireDTO(Tire tire) {
        if (tire == null) {
            return null;
        }

        return new TireDTO(
                tire.getId(),
                tire.getFireNumber(),
                tire.getBrand(),
                tire.getPsiPressure()
        );
    }

    public static TirePositionDTO toDto(TirePosition position) {
        if (position == null) {
            return null;
        }

        TireDTO tireDTO = toTireDTO(position.getTire());

        return new TirePositionDTO(
                position.getId(),
                position.getIdentifier(),
                position.isHasTire(),
                tireDTO
        );
    }

    public static Set<TirePositionDTO> toDtoSet(Set<TirePosition> positions) {
        if (positions == null) {
            return Set.of();
        }

        return positions.stream()
                .map(TirePositionsMapper::toDto)
                .collect(Collectors.toSet());
    }
}