package com.example.lms_system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.lms_system.dto.response.SlotResponse;
import com.example.lms_system.repository.SlotRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SlotService {
    private final SlotRepository slotRepository;

    public List<SlotResponse> getAllSlots() {
        return slotRepository.findAll().stream()
                .map(t -> SlotResponse.builder()
                        .slotId(t.getSlotId())
                        .startTime(t.getStartTime())
                        .endTime(t.getEndTime())
                        .build())
                .toList();
    }
}
