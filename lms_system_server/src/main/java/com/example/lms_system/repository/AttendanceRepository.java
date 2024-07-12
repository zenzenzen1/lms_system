package com.example.lms_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lms_system.entity.*;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {}
