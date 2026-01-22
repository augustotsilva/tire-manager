package com.tiremanager.repository;

import com.tiremanager.domain.Tire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TireRepository extends JpaRepository<Tire, Long> {

    boolean existsByFireNumber(Integer fireNumber);

    Optional<Tire> findByFireNumber(Integer fireNumber);
}
