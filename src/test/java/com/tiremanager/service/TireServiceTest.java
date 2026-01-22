package com.tiremanager.service;

import com.tiremanager.domain.Tire;
import com.tiremanager.domain.enums.TireBrandEnum;
import com.tiremanager.repository.TireRepository;
import com.tiremanager.service.dto.TireDTO;
import com.tiremanager.service.mapper.TireMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TireServiceTest {

    @InjectMocks
    private TireService tireService;

    @Mock
    private TireRepository tireRepository;

    @Mock
    private TireMapper tireMapper;

    private TireDTO createMockTireDTO(Integer fireNumber, boolean isAllocated) {
        return new TireDTO(null, fireNumber, TireBrandEnum.BRIDGESTONE, 90, isAllocated);
    }

    @Test
    void createTire_successfully() {
        TireDTO inputDTO = createMockTireDTO(1001, false);
        Tire savedTire = new Tire();
        TireDTO expectedDTO = new TireDTO(1L, 1001, TireBrandEnum.PIRELLI, 80, false);

        final ArgumentCaptor<Tire> tireArgumentCaptor = ArgumentCaptor.forClass(Tire.class);

        when(tireRepository.existsByFireNumber(inputDTO.getFireNumber())).thenReturn(false);
        when(tireRepository.save(any(Tire.class))).thenReturn(savedTire);
        when(tireMapper.toDto(savedTire)).thenReturn(expectedDTO);

        TireDTO resultDTO = tireService.createTire(inputDTO);

        verify(tireRepository).existsByFireNumber(inputDTO.getFireNumber());
        verify(tireRepository).save(tireArgumentCaptor.capture());
        verify(tireMapper).toDto(savedTire);

        Tire capturedTire = tireArgumentCaptor.getValue();
        assertEquals(inputDTO.getFireNumber(), capturedTire.getFireNumber());
        assertEquals(inputDTO.getBrand(), capturedTire.getBrand());
        assertEquals(inputDTO.getPsiPressure(), capturedTire.getPsiPressure());
        assertFalse(capturedTire.isAllocated());
        assertNull(capturedTire.getPosition());

        assertEquals(expectedDTO.getId(), resultDTO.getId());
        assertEquals(expectedDTO.getFireNumber(), resultDTO.getFireNumber());
    }

    @Test
    void createTire_with_allocated_true_successfully() {
        TireDTO inputDTO = createMockTireDTO(2002, true);
        Tire savedTire = new Tire();
        TireDTO expectedDTO = new TireDTO(2L, 2002, TireBrandEnum.BRIDGESTONE, 90, true);

        final ArgumentCaptor<Tire> tireArgumentCaptor = ArgumentCaptor.forClass(Tire.class);

        when(tireRepository.existsByFireNumber(inputDTO.getFireNumber())).thenReturn(false);
        when(tireRepository.save(any(Tire.class))).thenReturn(savedTire);
        when(tireMapper.toDto(savedTire)).thenReturn(expectedDTO);

        TireDTO resultDTO = tireService.createTire(inputDTO);

        verify(tireRepository).save(tireArgumentCaptor.capture());
        Tire capturedTire = tireArgumentCaptor.getValue();

        assertEquals(inputDTO.getFireNumber(), capturedTire.getFireNumber());
        assertTrue(capturedTire.isAllocated());
        assertNull(capturedTire.getPosition());

        assertEquals(expectedDTO.getFireNumber(), resultDTO.getFireNumber());
    }

    @Test
    void createTire_FireNumber_already_exists_throwsException() {
        TireDTO inputDTO = createMockTireDTO(2002, true);

        when(tireRepository.existsByFireNumber(inputDTO.getFireNumber())).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> tireService.createTire(inputDTO));

        assertEquals("Fire Number already exists!", exception.getMessage());

        verify(tireRepository, never()).save(any(Tire.class));
        verify(tireMapper, never()).toDto(any(Tire.class));
    }
}