package com.tiremanager.controller;

import com.tiremanager.service.TirePositionService;
import com.tiremanager.service.TireService;
import com.tiremanager.service.dto.TireAllocationByPlateDTO;
import com.tiremanager.service.dto.TireDTO;
import com.tiremanager.service.dto.TireDeallocationByPlateDTO;
import com.tiremanager.service.dto.TirePositionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tire")
public class TireController {

    private final TireService tireService;
    private final TirePositionService tirePositionService;

    public TireController(TireService tireService, TirePositionService tirePositionService) {
        this.tireService = tireService;
        this.tirePositionService = tirePositionService;
    }

    @GetMapping
    public ResponseEntity<List<TireDTO>> getAllTires() {
        return ResponseEntity.ok(tireService.getAllTires());
    }

    @GetMapping("/{fireNumber}")
    public ResponseEntity<TireDTO> getTireByFireNumber(@PathVariable Integer fireNumber) {
        return ResponseEntity.ok(tireService.getTireByFireNumber(fireNumber));
    }

    @PostMapping
    public ResponseEntity<TireDTO> createTire(@RequestBody TireDTO tireDTO) {
        return ResponseEntity.ok(tireService.createTire(tireDTO));
    }

    @PutMapping("/allocate")
    public ResponseEntity<TirePositionDTO> allocateTire(@RequestBody TireAllocationByPlateDTO tireAllocationByPlateDTO) {
        return ResponseEntity.ok(tirePositionService.allocateTire(tireAllocationByPlateDTO));
    }

    @PutMapping("/deallocate")
    public ResponseEntity<TirePositionDTO> deallocateTire(@RequestBody TireDeallocationByPlateDTO dto) {
        return ResponseEntity.ok(tirePositionService.deallocateTire(dto));
    }
}
