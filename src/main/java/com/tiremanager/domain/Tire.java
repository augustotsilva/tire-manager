package com.tiremanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tiremanager.domain.enums.TireBrandEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tire")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Tire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fire_number", unique = true, nullable = false)
    private Integer fireNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "brand", nullable = false)
    private TireBrandEnum brand;

    @Column(name = "psi_pressure")
    private Integer psiPressure;

    @Column(name = "is_allocated", nullable = false)
    private boolean isAllocated;

    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "tire")
    @JsonIgnore
    private TirePosition position;
}