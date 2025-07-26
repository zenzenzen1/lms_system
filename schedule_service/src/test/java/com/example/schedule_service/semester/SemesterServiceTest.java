package com.example.schedule_service.semester;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.schedule_service.base.BaseContainerTest;
import com.example.schedule_service.service.SemesterService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class SemesterServiceTest extends BaseContainerTest {
    @Container @ServiceConnection
    @SuppressWarnings("resource")
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withInitScript("sql/initdb_test.sql");


    @Autowired
    private SemesterService semesterService;

    @Test
    void getAllSemesters_shouldReturnAllSemesters() {
        var semesters = semesterService.getAllSemesters();
        assertEquals(5, semesters.size());
    }
}
