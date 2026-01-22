package com.tiremanager.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TireDeallocationByPlateDTO {
    private String licensePlate;
    private String tirePositionIdentifier;
}