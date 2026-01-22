package com.tiremanager.service;

import com.tiremanager.domain.Tire;
import com.tiremanager.domain.TirePosition;
import com.tiremanager.repository.TirePositionRepository;
import com.tiremanager.repository.TireRepository;
import com.tiremanager.service.dto.TireAllocationByPlateDTO;
import com.tiremanager.service.dto.TireDeallocationByPlateDTO;
import com.tiremanager.service.dto.TirePositionDTO;
import com.tiremanager.service.mapper.TirePositionsMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TirePositionServiceTest {

    @InjectMocks
    private TirePositionService tirePositionService;

    @Mock
    private TirePositionRepository tirePositionRepository;

    @Mock
    private TireRepository tireRepository;

    @Mock
    private TirePositionsMapper tirePositionsMapper;

    private TireAllocationByPlateDTO createMockAllocationDTO(Integer fireNumber, String plate, String identifier) {
        return new TireAllocationByPlateDTO(fireNumber, plate, identifier);
    }

    private TireDeallocationByPlateDTO createMockDeallocationDTO(String plate, String identifier) {
        return new TireDeallocationByPlateDTO(plate, identifier);
    }

    private TirePosition createMockTirePosition(boolean hasTire, Tire tire) {
        TirePosition position = new TirePosition();
        position.setHasTire(hasTire);
        position.setTire(tire);
        return position;
    }

    private Tire createMockTire(boolean isAllocated) {
        Tire tire = new Tire();
        tire.setAllocated(isAllocated);
        tire.setFireNumber(1001);
        return tire;
    }

    @Test
    void allocateTire_successfully() {
        TireAllocationByPlateDTO dto = createMockAllocationDTO(1001, "ABC1234", "FL");
        TirePosition mockPosition = createMockTirePosition(false, null);
        Tire mockTire = createMockTire(false);
        TirePosition savedPosition = createMockTirePosition(true, mockTire);
        TirePositionDTO expectedDTO = new TirePositionDTO();

        when(tirePositionRepository.findByVehicleLicensePlateAndIdentifier(dto.getLicensePlate(), dto.getTirePositionIdentifier()))
                .thenReturn(Optional.of(mockPosition));
        when(tireRepository.findByFireNumber(dto.getFireNumber())).thenReturn(Optional.of(mockTire));
        when(tirePositionRepository.save(any(TirePosition.class))).thenReturn(savedPosition);
        when(tirePositionsMapper.toDto(savedPosition)).thenReturn(expectedDTO);

        TirePositionDTO result = tirePositionService.allocateTire(dto);

        verify(tireRepository).save(mockTire);
        verify(tirePositionRepository).save(mockPosition);
        assertTrue(mockTire.isAllocated());
        assertTrue(mockPosition.isHasTire());
        assertEquals(expectedDTO, result);
    }

    @Test
    void allocateTire_PositionNotFound_throwsEntityNotFoundException() {
        TireAllocationByPlateDTO dto = createMockAllocationDTO(1002, "XYZ9876", "RR");

        when(tirePositionRepository.findByVehicleLicensePlateAndIdentifier(dto.getLicensePlate(), dto.getTirePositionIdentifier()))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> tirePositionService.allocateTire(dto));
        verify(tireRepository, never()).findByFireNumber(anyInt());
        verify(tireRepository, never()).save(any(Tire.class));
    }

    @Test
    void allocateTire_TireNotFound_throwsEntityNotFoundException() {
        TireAllocationByPlateDTO dto = createMockAllocationDTO(1001, "ABC1234", "FL");
        TirePosition mockPosition = createMockTirePosition(false, null);

        when(tirePositionRepository.findByVehicleLicensePlateAndIdentifier(dto.getLicensePlate(), dto.getTirePositionIdentifier()))
                .thenReturn(Optional.of(mockPosition));
        when(tireRepository.findByFireNumber(dto.getFireNumber())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> tirePositionService.allocateTire(dto));
        verify(tireRepository, never()).save(any(Tire.class));
    }

    @Test
    void allocateTire_PositionOccupied_throwsIllegalArgumentException() {
        TireAllocationByPlateDTO dto = createMockAllocationDTO(1001, "ABC1234", "FL");
        TirePosition mockPosition = createMockTirePosition(true, new Tire());
        Tire mockTire = createMockTire(false);

        when(tirePositionRepository.findByVehicleLicensePlateAndIdentifier(dto.getLicensePlate(), dto.getTirePositionIdentifier()))
                .thenReturn(Optional.of(mockPosition));
        when(tireRepository.findByFireNumber(dto.getFireNumber())).thenReturn(Optional.of(mockTire));

        assertThrows(IllegalArgumentException.class, () -> tirePositionService.allocateTire(dto));
        verify(tireRepository, never()).save(any(Tire.class));
    }

    @Test
    void allocateTire_TireAlreadyAllocated_throwsIllegalArgumentException() {
        TireAllocationByPlateDTO dto = createMockAllocationDTO(1001, "ABC1234", "FL");
        TirePosition mockPosition = createMockTirePosition(false, null);
        Tire mockTire = createMockTire(true); // Já alocado

        when(tirePositionRepository.findByVehicleLicensePlateAndIdentifier(dto.getLicensePlate(), dto.getTirePositionIdentifier()))
                .thenReturn(Optional.of(mockPosition));
        when(tireRepository.findByFireNumber(dto.getFireNumber())).thenReturn(Optional.of(mockTire));

        assertThrows(IllegalArgumentException.class, () -> tirePositionService.allocateTire(dto));
        verify(tireRepository, never()).save(any(Tire.class));
    }

    @Test
    void deallocateTire_successfully() {
        TireDeallocationByPlateDTO dto = createMockDeallocationDTO("ABC1234", "FL");
        Tire mockTire = createMockTire(true);
        TirePosition mockPosition = createMockTirePosition(true, mockTire);
        TirePosition savedPosition = new TirePosition(); // Posição desalocada
        TirePositionDTO expectedDTO = new TirePositionDTO();

        when(tirePositionRepository.findByVehicleLicensePlateAndIdentifier(dto.getLicensePlate(), dto.getTirePositionIdentifier()))
                .thenReturn(Optional.of(mockPosition));
        when(tirePositionRepository.save(any(TirePosition.class))).thenReturn(savedPosition);
        when(tirePositionsMapper.toDto(savedPosition)).thenReturn(expectedDTO);

        TirePositionDTO result = tirePositionService.deallocateTire(dto);

        verify(tireRepository).save(mockTire);
        verify(tirePositionRepository).save(mockPosition);
        assertFalse(mockTire.isAllocated());
        assertFalse(mockPosition.isHasTire());
        assertNull(mockPosition.getTire());
        assertNull(mockTire.getPosition());
        assertEquals(expectedDTO, result);
    }

    @Test
    void deallocateTire_PositionNotFound_throwsEntityNotFoundException() {
        TireDeallocationByPlateDTO dto = createMockDeallocationDTO("XYZ9876", "RR");

        when(tirePositionRepository.findByVehicleLicensePlateAndIdentifier(dto.getLicensePlate(), dto.getTirePositionIdentifier()))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> tirePositionService.deallocateTire(dto));
        verify(tireRepository, never()).save(any(Tire.class));
    }

    @Test
    void deallocateTire_PositionAlreadyEmpty_throwsIllegalArgumentException() {
        TireDeallocationByPlateDTO dto = createMockDeallocationDTO("ABC1234", "FL");
        TirePosition mockPosition = createMockTirePosition(false, null); // Posição vazia

        when(tirePositionRepository.findByVehicleLicensePlateAndIdentifier(dto.getLicensePlate(), dto.getTirePositionIdentifier()))
                .thenReturn(Optional.of(mockPosition));

        assertThrows(IllegalArgumentException.class, () -> tirePositionService.deallocateTire(dto));
        verify(tireRepository, never()).save(any(Tire.class));
    }
}