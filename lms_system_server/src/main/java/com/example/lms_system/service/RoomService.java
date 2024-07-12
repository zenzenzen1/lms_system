package com.example.lms_system.service;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.lms_system.entity.Room;
import com.example.lms_system.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public void saveRoom(Room room) {
        roomRepository.save(room);
    }

    public Room findById(Long id) {
        return roomRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Room not found"));
    }

    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }

    private Pageable createPageRequest(int page, int size) {
        return PageRequest.of(page, size);
    }

    public Page<Room> getRooms(int page, int size) {
        List<Room> rooms = roomRepository.findAll();

        Pageable pageRequest = createPageRequest(page, size);

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), rooms.size());

        List<Room> pageContent = rooms.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, rooms.size());
    }

    public List<Room> getRoomListFromPage(int page, int size) {
        Pageable pageRequest = createPageRequest(page, size);
        Page<Room> rooms = roomRepository.findAll(pageRequest);

        return rooms.hasContent() ? rooms.getContent() : Collections.emptyList();
    }
}
