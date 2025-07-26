package com.example.schedule_service.schedule;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import com.example.schedule_service.base.BaseContainerTest;
import com.example.schedule_service.entity.Schedule;
import com.example.schedule_service.entity.User;
import com.example.schedule_service.entity.dto.request.ScheduleRequest;
import com.example.schedule_service.entity.key.CourseStudentKey;
import com.example.schedule_service.exception.IdNotFoundException;
import com.example.schedule_service.repository.AttendanceRepository;
import com.example.schedule_service.repository.CourseRepository;
import com.example.schedule_service.repository.CourseStudentRepository;
import com.example.schedule_service.repository.RoomRepository;
import com.example.schedule_service.repository.SlotRepository;
import com.example.schedule_service.repository.UserRepository;
import com.example.schedule_service.service.ScheduleService;


public class ScheduleServiceTest extends BaseContainerTest{
    @Autowired private ScheduleService scheduleService;
    @Autowired private RoomRepository roomRepository;
    @Autowired private SlotRepository slotRepository;
    @Autowired private CourseRepository courseRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CourseStudentRepository courseStudentRepository;
    @Autowired private AttendanceRepository attendanceRepository;
    
    @Container @ServiceConnection
    @SuppressWarnings("resource")
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withInitScript("sql/initdb_test.sql")
    ;
    
    @Test
    void test_saveSchedule_shouldSuccess() throws IdNotFoundException{
        var room = roomRepository.findById(1L).orElseThrow(() -> new RuntimeException("Room not found"));
        var slot = slotRepository.findById(1L).orElseThrow(() -> new RuntimeException("Slot not found"));
        var course = courseRepository.findById(1L).orElseThrow(() -> new RuntimeException("Course not found"));
        Set<String> studentIds = Set.of("0790bcb9-b167-4c69-9435-099aa535d161", "28b88186-b4b7-429b-be9e-c3131721f8f9", "b60d965c-37db-465c-98d4-e03d7c2897f8"); 
        List<User> students = userRepository.findAllById(studentIds);
        var result = scheduleService.saveSchedules(ScheduleRequest.builder().roomId(room.getRoomId()).slotId(slot.getSlotId()).courseId(course.getCourseId()).studentIds(studentIds).build());
        assert result != null : "Schedules should not be null";
        assert !result.isEmpty() : "Schedules should not be empty";
        var courseStudents = courseStudentRepository.findAllById(studentIds.stream().map(id -> CourseStudentKey.builder().courseId(course.getCourseId()).studentId(id).build()).toList());
        assert courseStudents.size() == students.size() : "Course students size should match the number of students";
        for (var courseStudent : courseStudents) {
            assert courseStudent.getCourse().getCourseId().equals(course.getCourseId()) : "Course ID should match";
            assert students.stream().anyMatch(student -> student.getUserId().equals(courseStudent.getStudent().getUserId())) : "Student should be in the course";
            result.forEach(schedule -> {
                var attendance = attendanceRepository.findByStudent_UserIdAndSchedule_ScheduleId(courseStudent.getStudent().getUserId(), schedule.getScheduleId());
                assert attendance != null : "Attendance should not be null for student " + courseStudent.getStudent().getUserId() + " and schedule " + schedule.getScheduleId();
                assert attendance.getSchedule().getScheduleId().equals(schedule.getScheduleId()) : "Attendance schedule ID should match the saved schedule ID";
            });
        }
        List<Long> scheduleIds = result.stream().map(Schedule::getScheduleId).collect(Collectors.toList());
        var attendances = attendanceRepository.findAllBySchedule_ScheduleIdIn(scheduleIds);
        assert attendances.size() == result.size() * students.size() : "Attendance size should match the number of students' schedules";
        for (var attendance : attendances) {
            assert result.stream().anyMatch(schedule -> schedule.getScheduleId().equals(attendance.getSchedule().getScheduleId())) : "Attendance should match a schedule";
        }
        
        
    }
    
}
