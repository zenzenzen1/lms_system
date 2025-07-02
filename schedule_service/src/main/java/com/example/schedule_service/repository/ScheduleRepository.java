package com.example.schedule_service.repository;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.schedule_service.entity.Schedule;


public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query(
            value =
                    "select a.attendance_status, a.attendance_note, a.attendance_id, s.training_date, r.*, s.slot_id, s.subject_code, c.semester_code, c.course_id, c.teacher_id, su.subject_name from schedule s join room r on r.room_id = s.room_id join course c on s.course_id = c.course_id join attendance a on s.schedule_id = a.schedule_id join subject su on su.subject_code = c.subject_code where a.student_id = ?1 and s.training_date >= ?2 AND s.training_date <= ?3",
            nativeQuery = true)
    Set<Map<String, Object>> getScheduleByStudentId(String studentId, LocalDate startDate, LocalDate endDate);

    @Query(
            value =
                    "select cs.*, s.training_date, s.room_id, s.slot_id, s.subject_code, c.semester_code, c.course_id, c.teacher_id, su.subject_name from schedule s join course c on s.course_id = c.course_id join subject su on su.subject_code = c.subject_code join course_student cs on cs.course_id = c.course_id AND cs.student_id = ?1 and s.training_date >= ?2 AND s.training_date <= ?3",
            nativeQuery = true)
    Set<Map<String, Object>> getScheduleByTeacherId(String teacherId, LocalDate startDate, LocalDate endDate);

    @Query(
            value =
                    "select exists(select  s.*, c.teacher_id, c.semester_code from schedule s join course c on s.course_id = c.course_id where slot_id = ?1 and semester_code = ?2 and c.teacher_id = ?3)",
            nativeQuery = true)
    boolean existsBySemesterTeacherIdRoomId(Long slotId, String semesterCode, String teacherId);
}
