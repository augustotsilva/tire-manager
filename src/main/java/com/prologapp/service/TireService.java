package com.prologapp.service;

import com.prologapp.repository.TireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TireService {
    private final TireRepository tireRepository;
}
