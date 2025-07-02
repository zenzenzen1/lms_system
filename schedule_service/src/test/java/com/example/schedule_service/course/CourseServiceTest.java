package com.example.schedule_service.course;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.schedule_service.base.BaseContainerTest;
import com.example.schedule_service.service.CourseService;


public class CourseServiceTest extends BaseContainerTest {


    @Autowired
    private CourseService courseService;


    @BeforeEach
    void setUp() {
        // Semester semester = semesterRepository.save(Semester.builder()
        // .semesterCode("FA25")
        // .startDate(LocalDate.of(2025, 8, 1))
        // .endDate(LocalDate.of(2025, 12, 31))
        // .build());
        // User teacher = userRepository.save(User.builder()
        // .fullName("John Doe")
        // .email("john.doe@example.com")
        // .build());
        // courseRepository.save(Course.builder()
        // .subject(null)
        // .semester(semester)
        // .teacher(teacher)

        // .build());
    }

    @Test
    void test_findBySemesterCode_shouldSuccess() {
        var result = courseService.findBySemesterCode("SU25");
        assert !result.isEmpty() : "Courses should not be empty for semester SU25";
        assertEquals(4, result.size());
    }

}
