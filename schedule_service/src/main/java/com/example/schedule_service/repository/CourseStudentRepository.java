package com.example.schedule_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.schedule_service.entity.CourseStudent;
import com.example.schedule_service.entity.key.CourseStudentKey;

@Repository
public interface CourseStudentRepository extends JpaRepository<CourseStudent, CourseStudentKey> {
    List<CourseStudent> findByCourse_Semester_semesterCodeAndStudentId(String semesterCode, String studentId);
}
