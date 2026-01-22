package com.tiremanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiremanager.domain.enums.VehicleBrandEnum;
import com.tiremanager.service.VehicleService;
import com.tiremanager.service.dto.VehicleDTO;
import com.tiremanager.service.dto.VehicleNoTiresDTO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VehicleController.class)
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VehicleService vehicleService;

    private final String BASE_URL = "/v1/vehicle";

    private VehicleDTO createMockVehicleDTO(String plate) {
        return new VehicleDTO(1L, plate, VehicleBrandEnum.FORD_CARGO, 100.00, true, null);
    }

    private VehicleNoTiresDTO createMockVehicleNoTiresDTO() {
        return new VehicleNoTiresDTO(1L, "XYZ1234", VehicleBrandEnum.FREIGHTLINER, 50000.8, true);
    }

    @Test
    void getAllVehicles_shouldReturnOkAndList() throws Exception {
        List<VehicleNoTiresDTO> mockList = List.of(createMockVehicleNoTiresDTO());

        when(vehicleService.getAllVehicles()).thenReturn(mockList);

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(mockList.size()));

        verify(vehicleService).getAllVehicles();
    }

    @Test
    void getVehicleByLicensePlate_shouldReturnOkAndVehicle() throws Exception {
        String plate = "ABC1234";
        VehicleDTO mockDTO = createMockVehicleDTO(plate);

        when(vehicleService.getVehicleByLicensePlate(plate)).thenReturn(mockDTO);

        mockMvc.perform(get(BASE_URL + "/{plate}", plate))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.licensePlate").value(plate));

        verify(vehicleService).getVehicleByLicensePlate(plate);
    }

    @Test
    void getVehicleByLicensePlate_shouldReturnNotFound() throws Exception {
        String plate = "NOTFOUND";

        when(vehicleService.getVehicleByLicensePlate(plate)).thenThrow(new EntityNotFoundException("Not found"));

        mockMvc.perform(get(BASE_URL + "/{plate}", plate))
                .andExpect(status().isNotFound());

        verify(vehicleService).getVehicleByLicensePlate(plate);
    }

    @Test
    void createVehicle_shouldReturnOkAndCreatedVehicle() throws Exception {
        VehicleDTO inputDTO = createMockVehicleDTO("NEW1111");
        VehicleDTO createdDTO = createMockVehicleDTO("NEW1111");

        when(vehicleService.createVehicle(any(VehicleDTO.class))).thenReturn(createdDTO);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licensePlate").value(createdDTO.getLicensePlate()));

        verify(vehicleService).createVehicle(inputDTO);
    }

    @Test
    void createVehicle_shouldReturnBadRequestOnDuplicate() throws Exception {
        VehicleDTO inputDTO = createMockVehicleDTO("DUPLICATE");

        when(vehicleService.createVehicle(any(VehicleDTO.class)))
                .thenThrow(new IllegalArgumentException("License plate already exists!"));

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isBadRequest());
        verify(vehicleService).createVehicle(inputDTO);
    }
}