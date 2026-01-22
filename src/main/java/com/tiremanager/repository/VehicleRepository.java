package com.tiremanager.repository;

import com.tiremanager.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findVehicleByLicensePlate(String licensePlate);

    boolean existsByLicensePlate(String licensePlate);
}
