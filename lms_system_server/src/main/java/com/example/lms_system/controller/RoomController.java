package com.example.lms_system.controller;

import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lms_system.dto.response.ApiResponse;
import com.example.lms_system.dto.response.RoomResponse;
import com.example.lms_system.service.RoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = {"/rooms"})
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @GetMapping
    public ApiResponse<Set<RoomResponse>> getAllRooms() {
        return ApiResponse.<Set<RoomResponse>>builder()
                .result(roomService.getAllRooms())
                .build();
    }
}
