package com.prologapp.service.mapper;

import com.prologapp.domain.TirePosition;
import com.prologapp.service.dto.TireDTO;
import com.prologapp.service.dto.TirePositionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TirePositionsMapper {

    private final TireMapper tireMapper;

    public TirePositionDTO toDto(TirePosition position) {
        if (position == null) {
            return null;
        }

        TireDTO tireDTO = tireMapper.toDto(position.getTire());

        return new TirePositionDTO(
                position.getId(),
                position.getIdentifier(),
                position.isHasTire(),
                tireDTO
        );
    }

    public Set<TirePositionDTO> toDtoSet(Set<TirePosition> positions) {
        if (positions == null) {
            return Set.of();
        }

        return positions.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public TirePosition toEntity(TirePositionDTO dto) {
        if (dto == null) {
            return null;
        }

        TirePosition position = new TirePosition();
        position.setId(dto.getId());
        position.setIdentifier(dto.getIdentifier());
        position.setHasTire(dto.isHasTire());

        // Tire tire = tireMapper.toEntity(dto.getTire());
        // position.setTire(tire);

        return position;
    }


    public Set<TirePosition> toEntitySet(Set<TirePositionDTO> dtos) {
        if (dtos == null) {
            return Set.of();
        }
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toSet());
    }
}