package com.example.schedule_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.schedule_service.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findBySemester_SemesterCode(String semesterCode);
    List<Course> findBySemester_SemesterCodeAndCourseStudents_Student_Id(String semesterCode, String studentId);
}
