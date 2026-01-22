package com.tiremanager.domain;

import com.tiremanager.domain.enums.VehicleBrandEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "vehicle")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "license_plate", unique = true, nullable = false)
    private String licensePlate;

    @Enumerated(EnumType.STRING)
    @Column(name = "brand", nullable = false)
    private VehicleBrandEnum brand;

    @Column(name = "km")
    private Double km;

    @Column(name = "is_active")
    private boolean isActive;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TirePosition> positions;
}