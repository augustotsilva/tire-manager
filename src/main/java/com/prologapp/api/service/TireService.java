package com.prologapp.api.service;

import com.prologapp.domain.Tire;
import com.prologapp.domain.enums.TireBrandEnum;
import org.springframework.stereotype.Service;

@Service
public class TireService {

    public Tire getTireById(final Integer id) {
        return new Tire(1, 344, TireBrandEnum.BRIDGESTONE, 45, true);
    }
}
