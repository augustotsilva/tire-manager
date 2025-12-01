package com.prologapp.service.dto;

import com.prologapp.domain.enums.VehicleBrandEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleNoTiresDTO {
    private Long id;
    private String licensePlate;
    private VehicleBrandEnum brand;
    private Double km;
    private boolean isActive;
}
