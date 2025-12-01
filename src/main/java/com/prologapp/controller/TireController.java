package com.prologapp.controller;

import com.prologapp.service.TirePositionService;
import com.prologapp.service.TireService;
import com.prologapp.service.dto.TireAllocationByPlateDTO;
import com.prologapp.service.dto.TireDTO;
import com.prologapp.service.dto.TirePositionDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/tire")
public class TireController {

    private final TireService tireService;
    private final TirePositionService tirePositionService;

    public TireController(TireService tireService, TirePositionService tirePositionService) {
        this.tireService = tireService;
        this.tirePositionService = tirePositionService;
    }

    @PostMapping
    public TireDTO createTire(@RequestBody TireDTO tireDTO) {
        return tireService.createTire(tireDTO);
    }

    @PutMapping("/allocate")
    public TirePositionDTO allocateTire(@RequestBody TireAllocationByPlateDTO tireAllocationByPlateDTO) {
        return tirePositionService.allocateTire(tireAllocationByPlateDTO);
    }
}
