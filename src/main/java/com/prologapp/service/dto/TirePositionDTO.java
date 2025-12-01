package com.prologapp.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TirePositionDTO {
    private Long id;
    private String identifier;
    private boolean hasTire;
    private TireDTO tire;
}