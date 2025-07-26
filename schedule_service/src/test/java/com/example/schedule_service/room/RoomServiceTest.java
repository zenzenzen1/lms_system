package com.example.schedule_service.room;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import com.example.schedule_service.base.BaseContainerTest;
import com.example.schedule_service.service.RoomService;

public class RoomServiceTest extends BaseContainerTest {
    @Container
    @SuppressWarnings("resource")
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withInitScript("sql/initdb_test.sql");


    @Autowired
    private RoomService roomService;

    @Test
    void getAllRooms_shouldReturnAllRooms() {
        var rooms = roomService.getAllRooms();
        assertEquals(3, rooms.size());
    }
    
    @Test
    void getRoomById_shouldReturnRoom() {
        var room = roomService.findById(1L);
        assertEquals("AL-R301", room.getRoomNumber());
    }
    
    @Test
    void getRoomById_shouldThrowExceptionWhenRoomNotFound() {
        try {
            roomService.findById(999_999L);
        } catch (IllegalArgumentException e) {
            assertEquals("Room not found", e.getMessage());
        }
    }
}
