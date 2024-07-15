package com.example.lms_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.lms_system.entity.CourseStudent;
import com.example.lms_system.entity.key.CourseStudentKey;

@Repository
public interface CourseStudentRepository extends JpaRepository<CourseStudent, CourseStudentKey> {}
