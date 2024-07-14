package com.example.lms_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lms_system.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    // @Query(value = "SELECT * FROM schedule s  WHERE s.student_id = ?1", nativeQuery = true)
    Object getScheduleByStudentId(String studentId);
}
