package com.example.lms_system.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lms_system.dto.response.ApiResponse;
import com.example.lms_system.dto.response.SlotResponse;
import com.example.lms_system.service.SlotService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = {"/slots"})
public class SlotController {
    private final SlotService slotService;

    @GetMapping
    public ApiResponse<List<SlotResponse>> getAllSlots() {
        return ApiResponse.<List<SlotResponse>>builder()
                .result(slotService.getAllSlots())
                .build();
    }
}
