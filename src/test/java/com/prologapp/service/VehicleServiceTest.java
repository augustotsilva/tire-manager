package com.prologapp.service;

import com.prologapp.domain.TirePosition;
import com.prologapp.domain.Vehicle;
import com.prologapp.domain.enums.VehicleBrandEnum;
import com.prologapp.repository.VehicleRepository;
import com.prologapp.service.dto.VehicleDTO;
import com.prologapp.service.dto.VehicleNoTiresDTO;
import com.prologapp.service.mapper.TirePositionsMapper;
import com.prologapp.service.mapper.VehicleMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private VehicleMapper vehicleMapper;

    @Mock
    private TirePositionsMapper tirePositionsMapper;

    private VehicleDTO createMockVehicleDTO(String plate) {
        return new VehicleDTO(1L, plate, VehicleBrandEnum.FORD_CARGO, 132984.4, true, null);
    }

    private Vehicle createMockVehicle(String plate) {
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(plate);
        return vehicle;
    }

    @Test
    void getVehicleByLicensePlate_successfully() {
        String plate = "ABC1234";
        Vehicle mockVehicle = createMockVehicle(plate);
        VehicleDTO expectedDTO = createMockVehicleDTO(plate);

        when(vehicleRepository.findVehicleByLicensePlate(plate)).thenReturn(mockVehicle);
        when(vehicleMapper.toDto(mockVehicle)).thenReturn(expectedDTO);

        VehicleDTO result = vehicleService.getVehicleByLicensePlate(plate);

        verify(vehicleRepository).findVehicleByLicensePlate(plate);
        verify(vehicleMapper).toDto(mockVehicle);
        assertEquals(expectedDTO, result);
    }

    @Test
    void getVehicleByLicensePlate_notFound_throwsEntityNotFoundException() {
        String plate = "XYZ9999";

        when(vehicleRepository.findVehicleByLicensePlate(plate)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> vehicleService.getVehicleByLicensePlate(plate));
        verify(vehicleRepository).findVehicleByLicensePlate(plate);
        verify(vehicleMapper, never()).toDto(any());
    }

    @Test
    void getAllVehicles_successfully() {
        List<Vehicle> mockVehicles = List.of(createMockVehicle("ABC"));
        List<VehicleNoTiresDTO> expectedList = List.of(new VehicleNoTiresDTO());

        when(vehicleRepository.findAll()).thenReturn(mockVehicles);
        when(vehicleMapper.toDtoNoTiresList(mockVehicles)).thenReturn(expectedList);

        List<VehicleNoTiresDTO> result = vehicleService.getAllVehicles();

        verify(vehicleRepository).findAll();
        verify(vehicleMapper).toDtoNoTiresList(mockVehicles);
        assertEquals(expectedList, result);
    }

    @Test
    void createVehicle_successfully() {
        String plate = "NEW7890";
        VehicleDTO inputDTO = createMockVehicleDTO(plate);
        Vehicle savedVehicle = createMockVehicle(plate);
        VehicleDTO expectedDTO = createMockVehicleDTO(plate);
        Set<TirePosition> mockPositions = Set.of(new TirePosition());

        final ArgumentCaptor<Vehicle> vehicleCaptor = ArgumentCaptor.forClass(Vehicle.class);

        when(vehicleRepository.existsByLicensePlate(plate)).thenReturn(false);
        when(tirePositionsMapper.toEntitySet(inputDTO.getTirePositions())).thenReturn(mockPositions);
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(savedVehicle);
        when(vehicleMapper.toDto(savedVehicle)).thenReturn(expectedDTO);

        VehicleDTO result = vehicleService.createVehicle(inputDTO);

        verify(vehicleRepository).existsByLicensePlate(plate);
        verify(vehicleRepository).save(vehicleCaptor.capture());
        verify(vehicleMapper).toDto(savedVehicle);

        Vehicle capturedVehicle = vehicleCaptor.getValue();
        assertEquals(plate, capturedVehicle.getLicensePlate());
        assertEquals(mockPositions, capturedVehicle.getPositions());

        assertEquals(expectedDTO, result);
    }

    @Test
    void createVehicle_licensePlateExists_throwsIllegalArgumentException() {
        String plate = "EXIST111";
        VehicleDTO inputDTO = createMockVehicleDTO(plate);

        when(vehicleRepository.existsByLicensePlate(plate)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> vehicleService.createVehicle(inputDTO));

        verify(vehicleRepository).existsByLicensePlate(plate);
        verify(vehicleRepository, never()).save(any(Vehicle.class));
    }
}