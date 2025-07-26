package com.example.schedule_service.room;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import com.example.schedule_service.base.BaseContainerTest;
import com.example.schedule_service.service.RoomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RoomServiceTest extends BaseContainerTest {
    @Container
    @SuppressWarnings("resource")
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withInitScript("sql/initdb_test.sql");

    @Autowired private RedisTemplate<String, Object> redisTemplate;
    @Autowired private RoomService roomService;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void getAllRooms_shouldReturnAllRooms() {
        var rooms = roomService.getAllRooms();
        try {
            redisTemplate.opsForValue().set("rooms", objectMapper.writeValueAsString(rooms), Duration.ofMinutes(5));
            assertEquals(3, ((List<Object>)objectMapper.readValue((String)redisTemplate.opsForValue().get("rooms"), new TypeReference<List<Object>>() {})).size());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        assertNotNull(redisTemplate.opsForValue().get("rooms"), "Redis should contain the rooms data");
        assertEquals(3, rooms.size());
    }
}
