package com.example.lms_system.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.lms_system.dto.response.RoomResponse;
import com.example.lms_system.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public Set<RoomResponse> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(room -> RoomResponse.builder()
                        .roomId(room.getRoomId())
                        .roomNumber(room.getRoomNumber())
                        .build())
                .collect(Collectors.toSet());
    }
}
