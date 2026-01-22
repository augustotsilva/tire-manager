package com.tiremanager.service.dto;

import com.tiremanager.domain.enums.VehicleBrandEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class VehicleDTO {
    private Long id;
    private String licensePlate;
    private VehicleBrandEnum brand;
    private Double km;
    private boolean isActive;
    private Set<TirePositionDTO> tirePositions;
}
