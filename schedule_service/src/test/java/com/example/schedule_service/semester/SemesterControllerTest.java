package com.example.schedule_service.semester;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.schedule_service.entity.Semester;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class SemesterControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    
    @Container @ServiceConnection
    @SuppressWarnings("resource")
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withInitScript("sql/initdb_test.sql")
    ;
    
    @Test
    void saveSemester_shouldSuccess() throws Exception {
        // Given - create a new semester
        Semester semester = Semester.builder().semesterCode("SU26")
                .startDate(LocalDate.of(2025, 6, 1))
                .endDate(LocalDate.of(2025, 8, 31))
                .build();
        var semesterJson = objectMapper.writeValueAsString(semester);
        // When - save the semester
        mockMvc.perform(MockMvcRequestBuilders
                .post("/semesters")
                .contentType("application/json")
                .content(semesterJson)
                .accept("application/json")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                
                ;
        // Then - verify the semester is saved
    }
}
