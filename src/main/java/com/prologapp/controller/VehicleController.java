package com.prologapp.controller;

import com.prologapp.service.VehicleService;
import com.prologapp.service.dto.VehicleDTO;
import com.prologapp.service.dto.VehicleNoTiresDTO;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public VehicleDTO createVehicle(@RequestBody VehicleDTO vehicleDTO) {
        return vehicleService.createVehicle(vehicleDTO);
    }
}
