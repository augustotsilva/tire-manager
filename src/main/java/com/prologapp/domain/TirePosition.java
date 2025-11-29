package com.prologapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TirePosition {
    private String identifier;
    private boolean hasTire;
    private Tire tire;
}
