package com.prologapp.repository;

import com.prologapp.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Vehicle findVehicleByLicensePlate(String licensePlate);

    boolean existsByLicensePlate(String licensePlate);
}
