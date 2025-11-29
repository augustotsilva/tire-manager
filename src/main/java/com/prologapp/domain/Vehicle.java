package com.prologapp.domain;

import com.prologapp.domain.enums.VehicleBrandEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Vehicle {
    private Integer id;
    private String licensePlate;
    private VehicleBrandEnum brand;
    private Double km;
    private boolean isActive;
    private List<TirePosition> tirePositionList;
}
