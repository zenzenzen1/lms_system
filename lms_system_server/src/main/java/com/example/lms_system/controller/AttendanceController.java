package com.example.lms_system.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.lms_system.dto.request.AttendanceRequest;
import com.example.lms_system.dto.response.AttendanceResponse;
import com.example.lms_system.entity.Attendance;
import com.example.lms_system.service.AttendanceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = {"/attendances", "/attendances/"})
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PutMapping(value = "/saveAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object saveAllAttendance(@RequestBody AttendanceRequest attendanceRequests) {
        // return attendanceRequests;
        return attendanceService.saveAllAttendance(attendanceRequests.getAttendanceRequests());
    }

    @GetMapping("/scheduleId/{scheduleId}")
    public Set<Attendance> getStudentsByScheduleId(@PathVariable Long scheduleId) {
        return attendanceService.getStudentsByScheduleId(scheduleId);
    }

    @GetMapping("/teacherId/{teacherId}")
    public Object getScheduleByTeacherId(
            @PathVariable String teacherId, @RequestParam String startDate, @RequestParam String endDate) {
        try {
            var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-M-d").withLocale(Locale.CHINA);
            LocalDate start = LocalDate.parse(startDate, dateTimeFormatter);
            LocalDate end = LocalDate.parse(endDate, dateTimeFormatter);
            System.out.println(
                    start.format(dateTimeFormatter) + " " + end.format(dateTimeFormatter) + " " + start + " " + end);
            return attendanceService.getScheduleByTeacherId(teacherId, start, end);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Attendance>> getAttendances(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<Attendance> attendancePage = attendanceService.getAttendances(page, size);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Attendance-page-number", String.valueOf(attendancePage.getNumber()));
        headers.add("Attendance-page-size", String.valueOf(attendancePage.getSize()));
        return ResponseEntity.ok().headers(headers).body(attendancePage);
    }

    @GetMapping("/details")
    public ResponseEntity<Attendance> getAttendanceDetails(@RequestParam Long id) {
        return ResponseEntity.ok(attendanceService.findById(id));
    }

    @PostMapping("/add")
    public AttendanceResponse addAttendance(@RequestBody AttendanceRequest request) {
        return attendanceService.insertAttendance(request);
    }

    @PutMapping("/update/{id}")
    public AttendanceResponse updateAttendance(@RequestBody AttendanceRequest request) {
        return attendanceService.updateAttendance(request);
    }

    @PostMapping("/status/change/{id}")
    public ResponseEntity<Void> changeAttendanceStatus(@PathVariable Long id) {
        Attendance attendance = attendanceService.findById(id);
        if (attendance == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        attendanceService.changeAttendanceStatus(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
