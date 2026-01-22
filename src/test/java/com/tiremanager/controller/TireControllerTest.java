package com.tiremanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiremanager.domain.enums.TireBrandEnum;
import com.tiremanager.service.TirePositionService;
import com.tiremanager.service.TireService;
import com.tiremanager.service.dto.TireAllocationByPlateDTO;
import com.tiremanager.service.dto.TireDTO;
import com.tiremanager.service.dto.TireDeallocationByPlateDTO;
import com.tiremanager.service.dto.TirePositionDTO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TireController.class)
class TireControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TireService tireService;

    @MockBean
    private TirePositionService tirePositionService;

    private final String BASE_URL = "/v1/tire";

    private TireDTO createMockTireDTO(Integer fireNumber) {
        return new TireDTO(null, fireNumber, TireBrandEnum.MICHELIN, 80, false);
    }

    private TireAllocationByPlateDTO createMockAllocationDTO(Integer fireNumber) {
        return new TireAllocationByPlateDTO(fireNumber, "ABC1234", "FL");
    }


    private TireDeallocationByPlateDTO createMockDeallocationDTO() {
        return new TireDeallocationByPlateDTO("ABC1234", "FL");
    }

    private TirePositionDTO createMockTirePositionDTO() {
        return new TirePositionDTO(1L, "FL", true, null);
    }

    @Test
    void createTire_shouldReturnOkAndCreatedTire() throws Exception {
        TireDTO inputDTO = createMockTireDTO(1001);
        TireDTO createdDTO = new TireDTO(1L, 1001, TireBrandEnum.MICHELIN, 80, false);

        when(tireService.createTire(any(TireDTO.class))).thenReturn(createdDTO);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fireNumber").value(1001));

        verify(tireService).createTire(inputDTO);
    }

    @Test
    void createTire_shouldReturnBadRequestOnDuplicateFireNumber() throws Exception {
        TireDTO inputDTO = createMockTireDTO(999);

        when(tireService.createTire(any(TireDTO.class)))
                .thenThrow(new IllegalArgumentException("Fire Number already exists!"));

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());

        verify(tireService).createTire(inputDTO);
    }

    @Test
    void allocateTire_shouldReturnOkAndPositionDTO() throws Exception {
        TireAllocationByPlateDTO inputDTO = createMockAllocationDTO(1001);
        TirePositionDTO allocatedDTO = createMockTirePositionDTO();

        when(tirePositionService.allocateTire(any(TireAllocationByPlateDTO.class))).thenReturn(allocatedDTO);

        mockMvc.perform(put(BASE_URL + "/allocate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hasTire").value(true));

        verify(tirePositionService).allocateTire(inputDTO);
    }

    @Test
    void allocateTire_shouldReturnNotFoundWhenTireOrPositionMissing() throws Exception {
        TireAllocationByPlateDTO inputDTO = createMockAllocationDTO(999);

        when(tirePositionService.allocateTire(any(TireAllocationByPlateDTO.class)))
                .thenThrow(new EntityNotFoundException("Tire not found."));

        mockMvc.perform(put(BASE_URL + "/allocate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isNotFound());

        verify(tirePositionService).allocateTire(inputDTO);
    }

    @Test
    void deallocateTire_shouldReturnOkAndPositionDTO() throws Exception {
        TireDeallocationByPlateDTO inputDTO = createMockDeallocationDTO();
        TirePositionDTO deallocatedDTO = createMockTirePositionDTO();
        deallocatedDTO.setHasTire(false);

        when(tirePositionService.deallocateTire(any(TireDeallocationByPlateDTO.class))).thenReturn(deallocatedDTO);

        mockMvc.perform(put(BASE_URL + "/deallocate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hasTire").value(false));

        verify(tirePositionService).deallocateTire(inputDTO);
    }

    @Test
    void deallocateTire_shouldReturnBadRequestWhenPositionIsEmpty() throws Exception {
        TireDeallocationByPlateDTO inputDTO = createMockDeallocationDTO();

        when(tirePositionService.deallocateTire(any(TireDeallocationByPlateDTO.class)))
                .thenThrow(new IllegalArgumentException("Position is already empty."));

        mockMvc.perform(put(BASE_URL + "/deallocate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());

        verify(tirePositionService).deallocateTire(inputDTO);
    }
}