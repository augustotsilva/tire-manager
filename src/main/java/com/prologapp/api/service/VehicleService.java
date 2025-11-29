package com.prologapp.api.service;

import com.prologapp.domain.Tire;
import com.prologapp.domain.TirePosition;
import com.prologapp.domain.Vehicle;
import com.prologapp.domain.enums.VehicleBrandEnum;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private final TireService tireService;

    public VehicleService(TireService tireService) {
        this.tireService = tireService;
    }

    public Vehicle getVehicleById(final Integer id) {
        Tire tire = tireService.getTireById(id);
        TirePosition tirePosition = new TirePosition("A", true, tire);
        return new Vehicle(1, "DBZ8001", VehicleBrandEnum.SCANIA, 1200.1, true, List.of(tirePosition));
    }

    public List<Vehicle> getAllVehicles() {
        Tire tire = tireService.getTireById(1);
        TirePosition tirePosition = new TirePosition("A", true, tire);
        return List.of(
                new Vehicle(1, "DBZ8001", VehicleBrandEnum.SCANIA, 1100.1, true, List.of(tirePosition)),
                new Vehicle(2, "DBZ8002", VehicleBrandEnum.VOLVO, 1200.2, true, List.of(tirePosition)),
                new Vehicle(3, "DBZ8003", VehicleBrandEnum.IVECO, 1300.3, true, List.of(tirePosition)));
    }
}
