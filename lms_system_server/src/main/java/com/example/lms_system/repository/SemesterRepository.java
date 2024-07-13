package com.example.lms_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lms_system.entity.Semester;

public interface SemesterRepository extends JpaRepository<Semester, String> {}
