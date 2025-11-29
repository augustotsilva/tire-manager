package com.prologapp.api.controller;

import com.prologapp.domain.Vehicle;
import com.prologapp.domain.enums.VehicleBrandEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/vehicle")
public class VehicleController {

    @GetMapping("/list")
    public List<Vehicle> listVehicles() {
        return List.of(new Vehicle(1, "DBZ8001", VehicleBrandEnum.SCANIA, 1200.1, true, List.of()));
    }
}
