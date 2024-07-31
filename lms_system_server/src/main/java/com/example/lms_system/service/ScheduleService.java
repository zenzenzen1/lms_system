package com.example.lms_system.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.kafka_dto.ScheduleStudent;
import com.example.lms_system.dto.request.ScheduleRequest;
import com.example.lms_system.entity.Attendance;
import com.example.lms_system.entity.CourseStudent;
import com.example.lms_system.entity.Schedule;
import com.example.lms_system.entity.key.CourseStudentKey;
import com.example.lms_system.repository.AttendanceRepository;
import com.example.lms_system.repository.CourseRepository;
import com.example.lms_system.repository.CourseStudentRepository;
import com.example.lms_system.repository.RoomRepository;
import com.example.lms_system.repository.ScheduleRepository;
import com.example.lms_system.repository.SemesterRepository;
import com.example.lms_system.repository.SlotRepository;
import com.example.lms_system.repository.UserRepository;
import com.example.lms_system.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final SemesterRepository semesterRepository;
    private final SlotRepository slotRepository;
    private final RoomRepository roomRepository;
    private final CourseRepository courseRepository;
    private final CourseStudentRepository courseStudentRepository;
    private final AttendanceRepository attendanceRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    // private final ObjectMapper redisObjectMapper;

    @Value("${app.redis.ttl_student_schedule}")
    private int ttlStudentSchedule;

    public Object getScheduleByStudentId(String studentId, LocalDate startDate, LocalDate endDate) {
        // var schedules = scheduleRepository.findAll().stream().filter(t -> t.get)

        ObjectMapper redisObjectMapper = new ObjectMapper();
        String key = "schedule" + studentId + "/" + startDate.toString() + "->" + endDate.toString();
        List<Object> schedulesRedis = null;
        try {
            String json = (String) redisTemplate.opsForValue().get(key);
            schedulesRedis =
                    json != null ? redisObjectMapper.readValue(json, new TypeReference<List<Object>>() {}) : null;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (schedulesRedis == null) {
            try {
                var schedules = scheduleRepository.getScheduleByStudentId(studentId, startDate, endDate);
                String json = redisObjectMapper.writeValueAsString(schedules);
                redisTemplate.opsForValue().set(key, json, Duration.ofSeconds(ttlStudentSchedule));
                return schedules;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            return schedulesRedis;
        }

        return scheduleRepository.getScheduleByStudentId(studentId, startDate, endDate);
    }

    // save schedules for students in specific slot, semester, course, room
    public Set<Schedule> saveSchedules(ScheduleRequest scheduleRequest) {
        System.out.println(scheduleRequest);
        if (scheduleRequest.getStudentIds() == null
                || scheduleRequest.getStudentIds().isEmpty()) {
            // validate
            return null;
        }

        var students = userRepository.findAllById(scheduleRequest.getStudentIds());

        var slot = slotRepository.findById(scheduleRequest.getSlotId());
        var semester = semesterRepository.findById(scheduleRequest.getSemesterCode());
        var course = courseRepository.findById(scheduleRequest.getCourseId());
        var room = roomRepository.findById(scheduleRequest.getRoomId());
        Set<Schedule> schedules = new HashSet<>();

        if (slot.isPresent() && semester.isPresent() && course.isPresent() && room.isPresent()) {
            var now = LocalDate.now();
            for (
            // var i = semester.get().getStartDate();
            var i = now;
                    // i.isBefore(semester.get().getEndDate());
                    i.isBefore(now.plusDays(10));
                    i = i.plusDays(1)) {
                //
                if (i.isAfter(now) && !Utils.checkIsWeekend(i)) {
                    var schedule = scheduleRepository.save(Schedule.builder()
                            .trainingDate(i)
                            .course(course.get())
                            .room(room.get())
                            .slot(slot.get())
                            .subject(course.get().getSubject())
                            .build());
                    schedules.add(schedule);
                    students.forEach((student) -> {
                        courseStudentRepository.save(CourseStudent.builder()
                                .course(course.get())
                                .student(student)
                                .id(CourseStudentKey.builder()
                                        .courseId(course.get().getCourseId())
                                        .studentId(student.getId())
                                        .build())
                                .build());
                        attendanceRepository.save(Attendance.builder()
                                .student(student)
                                .schedule(schedule)
                                .build());

                        redisTemplate.delete(redisTemplate.keys("schedule" + student.getId() + "*"));
                    });
                }
            }
            students.forEach((student) -> {
                kafkaTemplate.send(
                        "create-schedule",
                        ScheduleStudent.builder()
                                .createdAt(LocalDateTime.now())
                                .studentId(student.getId())
                                .build());
            });
        }
        // redisTemplate.delete(null)

        // validate
        return schedules;
    }

    public void saveSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    public void deleteById(Long id) {
        scheduleRepository.deleteById(id);
    }

    public Schedule findById(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Schedule not found"));
    }

    public Object getSchedules() {
        return scheduleRepository.findAll();
    }

    public Page<Schedule> getSchedules(int page, int size) {
        List<Schedule> list = scheduleRepository.findAll();

        Pageable pageRequest = createPageRequest(page, size);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), list.size());

        List<Schedule> pageContent = list.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, list.size());
    }

    private Pageable createPageRequest(int page, int size) {
        return PageRequest.of(page, size);
    }

    public List<Schedule> getScheduleListFromPage(int page, int size) {
        Pageable pageRequest = createPageRequest(page, size);
        Page<Schedule> scheduleList = scheduleRepository.findAll(pageRequest);

        return scheduleList.hasContent() ? scheduleList.getContent() : Collections.emptyList();
    }
}
