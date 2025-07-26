package com.example.schedule_service.course;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import com.example.schedule_service.base.BaseContainerTest;
import com.example.schedule_service.service.CourseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CourseServiceTest extends BaseContainerTest {
    @Container @SuppressWarnings("resource") 
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withInitScript("sql/initdb_test.sql");
    
    @Autowired
    private CourseService courseService;
    
    @Autowired private RedisTemplate<String, Object> redisTemplate;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void test_findBySemesterCode_shouldSuccess() {
        // When
        var result = courseService.findBySemesterCode("SU25");
        try {
            redisTemplate.opsForValue().set("course:SU25", objectMapper.writeValueAsString(result.stream().map(course -> {
                course.setCourseStudents(null);
                course.getTeacher().setCourseStudents(null);
                return course;
            }).toList()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // Then
        assertNotNull(redisTemplate.opsForValue().get("course:SU25"), "Redis should contain the course data for SU25");
        assertNotNull(result, "Result should not be null");
        assertNull(redisTemplate.opsForValue().get("rooms"));
        assertFalse(result.isEmpty(), "Courses should not be empty for semester SU25");
        assertEquals(4, result.size(), "Should have 4 courses for semester SU25");
    }
}
