package com.example.lms_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lms_system.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {}
