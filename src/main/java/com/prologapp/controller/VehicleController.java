package com.prologapp.controller;

import com.prologapp.service.VehicleService;
import com.prologapp.service.dto.VehicleDTO;
import com.prologapp.service.dto.VehicleNoTiresDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/list")
    public List<VehicleNoTiresDTO> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @GetMapping("/{plate}")
    public VehicleDTO getVehicleByLicensePlate(@PathVariable String plate) {
        return vehicleService.getVehicleByLicensePlate(plate);
    }
}
