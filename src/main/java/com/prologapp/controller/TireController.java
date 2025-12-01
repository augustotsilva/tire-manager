package com.prologapp.controller;

import com.prologapp.service.TireService;
import com.prologapp.service.dto.TireDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/tire")
public class TireController {

    private final TireService tireService;

    public TireController(TireService tireService) {
        this.tireService = tireService;
    }

    @PostMapping
    public TireDTO createTire(@RequestBody TireDTO tireDTO) {
        return tireService.createTire(tireDTO);
    }
}
