package com.tiremanager.repository;

import com.tiremanager.domain.TirePosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TirePositionRepository extends JpaRepository<TirePosition, Long> {

    Optional<TirePosition> findByVehicleLicensePlateAndIdentifier(String licensePlate, String identifier);
}
