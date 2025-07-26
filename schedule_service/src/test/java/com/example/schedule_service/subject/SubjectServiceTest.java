package com.example.schedule_service.subject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import com.example.schedule_service.base.BaseContainerTest;
import com.example.schedule_service.entity.Subject;
import com.example.schedule_service.repository.SubjectRepository;
import com.example.schedule_service.service.SubjectService;

public class SubjectServiceTest extends BaseContainerTest {
    @Container @ServiceConnection
    @SuppressWarnings("resource")
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withInitScript("sql/initdb_test.sql")
    ;
    
    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectRepository subjectRepository;

    @Test
    void testSaveSubject() {
        
        // When - save new subject
        Subject subject = Subject.builder().subjectCode("MAT101").subjectName("Math").status(true).build();
        subjectService.saveSubject(subject);
        
        // Then
        List<Subject> all = subjectRepository.findAll();
        assertEquals(5, all.size());
        
        Subject savedSubject = subjectRepository.findById("MAT101").orElse(null);
        assertNotNull(savedSubject);
        assertEquals("MAT101", savedSubject.getSubjectCode());
        assertEquals("Math", savedSubject.getSubjectName());
    }

    @Test
    void test_findById_shouldSuccess() {
        // Given - save a subject for this test
        Subject subject = Subject.builder().subjectCode("MAT101").subjectName("Math").status(true).build();
        subjectRepository.save(subject);
        
        // When
        Subject found = subjectService.findById("MAT101");

        // Then
        assertNotNull(found);
        assertEquals("MAT101", found.getSubjectCode());
        assertEquals("Math", found.getSubjectName());
    }
}
