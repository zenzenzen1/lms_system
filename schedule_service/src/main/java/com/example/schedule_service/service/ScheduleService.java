package com.example.schedule_service.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
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
import org.springframework.web.multipart.MultipartFile;

import com.example.event.ScheduleStudent;
import com.example.schedule_service.entity.Attendance;
import com.example.schedule_service.entity.CourseStudent;
import com.example.schedule_service.entity.Schedule;
import com.example.schedule_service.entity.dto.request.ScheduleRequest;
import com.example.schedule_service.entity.key.CourseStudentKey;
import com.example.schedule_service.exception.IdNotFoundException;
import com.example.schedule_service.repository.AttendanceRepository;
import com.example.schedule_service.repository.CourseRepository;
import com.example.schedule_service.repository.CourseStudentRepository;
import com.example.schedule_service.repository.RoomRepository;
import com.example.schedule_service.repository.ScheduleRepository;
import com.example.schedule_service.repository.SlotRepository;
import com.example.schedule_service.repository.UserRepository;
import com.example.schedule_service.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final SlotRepository slotRepository;
    private final RoomRepository roomRepository;
    private final CourseRepository courseRepository;
    private final CourseStudentRepository courseStudentRepository;
    private final AttendanceRepository attendanceRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ExcelService excelService;
    // private final ObjectMapper redisObjectMapper;

    @Value("${app.redis.ttl_student_schedule}")
    private int ttlStudentSchedule;

    public Object importFromExcel(MultipartFile files) {
        try {
            return excelService.getSchedulesFromExcelFile(files.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file");
        }
    }

    public void exportIntoExcelFile(HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=ScheduleFile" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Schedule> schedules = scheduleRepository.findAll();
        excelService.exportScheduleExcel(response, schedules);
    }

    public Object getScheduleByStudentId(String studentId, LocalDate startDate, LocalDate endDate) {
        // var schedules = scheduleRepository.findAll().stream().filter(t -> t.get)

        ObjectMapper redisObjectMapper = new ObjectMapper();
        String key = "schedule" + studentId + "/" + startDate.toString() + "->" + endDate.toString();
        List<Object> schedulesRedis = null;
        try {
            String json = (String) redisTemplate.opsForValue().get(key);
            schedulesRedis = json != null ? redisObjectMapper.readValue(json, new TypeReference<List<Object>>() {
            }) : null;
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
    // @Transactional
    public Set<Schedule> saveSchedules(ScheduleRequest scheduleRequest) throws IdNotFoundException {
        System.out.println(scheduleRequest);
        if (scheduleRequest.getStudentIds() == null
                || scheduleRequest.getStudentIds().isEmpty()) {
            // validate
            return null;
        }

        var students = userRepository.findAllById(scheduleRequest.getStudentIds());

        var slot = slotRepository.findById(scheduleRequest.getSlotId()).orElseThrow(() -> new IdNotFoundException("Slot not found"));
        // var semester =
        // semesterRepository.findById(scheduleRequest.getSemesterCode());
        var course = courseRepository.findById(scheduleRequest.getCourseId()).orElseThrow(() -> new IdNotFoundException("Course not found"));
        var room = roomRepository.findById(scheduleRequest.getRoomId()).orElseThrow(() -> new IdNotFoundException("Room not found"));
        Set<Schedule> schedules = new HashSet<>();

        for (var i = course.getSemester().getStartDate();
                // var i = now;
                i.isBefore(course.getSemester().getEndDate());
                // i.isBefore(now.plusDays(1)); 
                i = i.plusDays(1)) {
            if (
                // i.isAfter(now) && 
            !Utils.checkIsWeekend(i)) {
                var schedule = scheduleRepository.save(Schedule.builder()
                        .trainingDate(i)
                        .course(course)
                        .room(room)
                        .slot(slot)
                        .subject(course.getSubject())
                        .build());
                schedules.add(schedule);
                students.forEach((student) -> {
                    courseStudentRepository.save(CourseStudent.builder()
                            .course(course)
                            .student(student)
                            .id(CourseStudentKey.builder()
                                    .courseId(course.getCourseId())
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
