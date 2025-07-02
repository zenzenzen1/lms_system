package com.example.schedule_service.subject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.schedule_service.base.BaseContainerTest;
import com.example.schedule_service.entity.Subject;
import com.example.schedule_service.repository.SubjectRepository;
import com.example.schedule_service.service.SubjectService;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @Testcontainers
// @TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class SubjectServiceTest extends BaseContainerTest {

    // @Container
    // static PostgreSQLContainer<?> postgresContainer = new
    // PostgreSQLContainer<>("postgres")
    // .withDatabaseName("testdb")
    // .withUsername("testuser")
    // .withPassword("testpass");

    // @Container
    // @ServiceConnection
    // @SuppressWarnings("resource")
    // static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres"))
    //         .withDatabaseName("testdb")
    //         .withUsername("postgres")
    //         .withPassword("123")
    //         .withInitScript("sql/initdb_test.sql")
    //         ;

    // @DynamicPropertySource
    // static void configureDatasource(DynamicPropertyRegistry registry) {
    // registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
    // registry.add("spring.datasource.username", postgresContainer::getUsername);
    // registry.add("spring.datasource.password", postgresContainer::getPassword);
    // }
    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectRepository subjectRepository;

    @BeforeEach
    void setUp() {
        Subject subject = Subject.builder().subjectCode("MAT101").subjectName("Math").build();
        subjectRepository.save(subject);
    }

    // @BeforeAll
    // void beforeAll() {
    // postgresContainer.start();

    // }

    // @AfterAll
    // static void afterAll() {
    // postgresContainer.stop();
    // }

    @Test
    void testSaveSubject() {
        // Subject subject =
        // Subject.builder().subjectCode("MAT101").subjectName("Math").build();

        // subjectService.saveSubject(subject);
        // Then
        List<Subject> all = subjectRepository.findAll();
        assertEquals(5, all.size());
        assertEquals("MAT101", all.get(all.size() - 1).getSubjectCode());
    }

    @Test
    void test_findById_shouldSuccess() {
        // Given
        // Subject subject =
        // Subject.builder().subjectCode("MAT101").subjectName("Math").build();
        // subjectService.saveSubject(subject);

        // When
        Subject found = subjectService.findById("MAT101");

        // Then
        assertEquals("MAT101", found.getSubjectCode());
    }
}
