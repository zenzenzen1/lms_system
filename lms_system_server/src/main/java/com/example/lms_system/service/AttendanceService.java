package com.example.lms_system.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.lms_system.dto.request.AttendanceRequest;
import com.example.lms_system.entity.Attendance;
import com.example.lms_system.mapper.AttendanceMapper;
import com.example.lms_system.repository.AttendanceRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;

    @SuppressWarnings("unused")
    private final RedisTemplate<String, Object> redisTemplate;

    public double getAbsentPercentage(String studentId, long courseId) {
        long total = attendanceRepository.findAll().stream()
                .filter(t -> t.getStudent().getId().equals(studentId)
                        && t.getSchedule().getCourse().getCourseId() == courseId)
                .count();
        long numberOfAbsent = attendanceRepository.findAll().stream()
                .filter(t -> {
                    if (t.getStudent().getId().equals(studentId)
                            && t.getSchedule().getCourse().getCourseId() == courseId) {
                        return t.getAttendanceStatus() != null && !t.getAttendanceStatus();
                    }
                    return false;
                })
                .count();
        System.out.println(total + " " + numberOfAbsent);
        return ((numberOfAbsent * 1.0 / total) * 100);
    }

    public List<Attendance> getAttendancesByCourseIdStudentIdSlotId(Long cousreId, String studentId, int slotId) {
        return attendanceRepository.findAll().stream()
                .filter(t -> t.getSchedule().getCourse().getCourseId() == cousreId
                        && t.getStudent().getId().equals(studentId))
                .sorted((o1, o2) -> o1.getSchedule()
                                .getTrainingDate()
                                .isAfter(o2.getSchedule().getTrainingDate())
                        ? 1
                        : -1)
                .toList();
    }

    public Object getScheduleByTeacherId(String teacherId, LocalDate startate, LocalDate endDate) {

        // var schedules = scheduleRepository.findAll().stream()
        // .filter(t -> t.getTrainingDate().isAfter(endDate)
        // && t.getTrainingDate().isBefore(endDate))
        // .toList();
        // var a = attendanceRepository.findAll().stream()
        // .filter(t -> schedules.stream()
        // .filter(s -> s.getScheduleId() == t.getSchedule().getScheduleId())
        // .findFirst()
        // .isPresent())
        // .collect(Collectors.toSet());

        // return a.stream()
        // .filter(at -> courseRepository.findAll().stream()
        // .filter(c -> c.getTeacher().getId().equals(teacherId)
        // && c.getCourseId()
        // == at.getSchedule().getCourse().getCourseId())
        // .findFirst()
        // .isPresent())
        // .collect(Collectors.toSet());
        return attendanceRepository.getScheduleByTeacherId(teacherId, startate, endDate);
        // return scheduleRepository.getScheduleByTeacherId(teacherId, startate,
        // endDate);
    }

    public Set<Attendance> getStudentsByScheduleId(long scheduleId) {
        return attendanceRepository.findAll().stream()
                .filter(a -> a.getSchedule().getScheduleId() == scheduleId)
                .collect(Collectors.toSet());
    }

    public Set<Attendance> saveAllAttendance(Set<AttendanceRequest> attendance) {
        return attendanceRepository
                .saveAll(attendance.stream()
                        .map(t -> {
                            Attendance _attendance = attendanceRepository
                                    .findById(t.getAttendanceId())
                                    .orElse(null);
                            if (_attendance != null) {
                                attendanceMapper.updateAttendance(_attendance, t);
                                return _attendance;
                            }
                            return null;
                        })
                        .toList())
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public void saveAttendance(Attendance attendance) {
        attendanceRepository.save(attendance);
    }

    public void deleteById(Long id) {
        attendanceRepository.deleteById(id);
    }

    public Attendance findById(Long id) {
        return attendanceRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Attendance not found."));
    }

    public Page<Attendance> getAttendances(int page, int size) {
        List<Attendance> list = attendanceRepository.findAll();

        Pageable pageRequest = createPageRequest(page, size);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), list.size());

        List<Attendance> pageContent = list.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, list.size());
    }

    private Pageable createPageRequest(int page, int size) {
        return PageRequest.of(page, size);
    }

    public List<Attendance> getAttendanceListFromPage(int page, int size) {
        Pageable pageRequest = createPageRequest(page, size);
        Page<Attendance> attendanceList = attendanceRepository.findAll(pageRequest);

        return attendanceList.hasContent() ? attendanceList.getContent() : Collections.emptyList();
    }
}
