package com.example.schedule_service.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import com.example.schedule_service.base.BaseContainerTest;
import com.example.schedule_service.service.CourseService;

public class CourseServiceTest extends BaseContainerTest {
    @Container @ServiceConnection
    @SuppressWarnings("resource")
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withInitScript("sql/initdb_test.sql")
    ;
    @Autowired
    private CourseService courseService;

    @Test
    void test_findBySemesterCode_shouldSuccess() {
        // When
        var result = courseService.findBySemesterCode("SU25");
        
        // Then
        assertNotNull(result, "Result should not be null");
        assertFalse(result.isEmpty(), "Courses should not be empty for semester SU25");
        assertEquals(4, result.size(), "Should have 4 courses for semester SU25");
    }
}
