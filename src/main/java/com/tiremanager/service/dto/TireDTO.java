package com.tiremanager.service.dto;

import com.tiremanager.domain.enums.TireBrandEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TireDTO {
    private Long id;
    private Integer fireNumber;
    private TireBrandEnum brand;
    private Integer psiPressure;
    private boolean isAllocated;
}