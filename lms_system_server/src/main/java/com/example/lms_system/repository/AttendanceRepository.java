package com.example.lms_system.repository;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.lms_system.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    @Query(
            value =
                    "select s.*, a.*, sl.start_time as slot_start_time, sl.end_time slot_end_time from attendance a join schedule s on a.schedule_id = s.schedule_id join room r on s.room_id = r.room_id join course c on c.course_id = s.course_id join slot sl on sl.slot_id = s.slot_id where c.teacher_id = ?1 and s.training_date >= ?2 and s.training_date <= ?3",
            nativeQuery = true)
    Set<Map<String, Object>> getScheduleByTeacherId(String teacherId, LocalDate startDate, LocalDate endDate);
}
