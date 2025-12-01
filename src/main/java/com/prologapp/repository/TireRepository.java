package com.prologapp.repository;

import com.prologapp.domain.Tire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TireRepository extends JpaRepository<Tire, Long> {
}
