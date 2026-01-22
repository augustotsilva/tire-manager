package com.tiremanager.controller;

import com.tiremanager.service.VehicleService;
import com.tiremanager.service.dto.VehicleDTO;
import com.tiremanager.service.dto.VehicleNoTiresDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping()
    public ResponseEntity<List<VehicleNoTiresDTO>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    @GetMapping("/{plate}")
    public ResponseEntity<VehicleDTO> getVehicleByLicensePlate(@PathVariable String plate) {
        return ResponseEntity.ok(vehicleService.getVehicleByLicensePlate(plate));
    }

    @PostMapping
    public ResponseEntity<VehicleDTO> createVehicle(@RequestBody VehicleDTO vehicleDTO) {
        return ResponseEntity.ok(vehicleService.createVehicle(vehicleDTO));
    }
}
