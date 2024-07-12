package com.example.lms_system.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.lms_system.entity.Attendance;
import com.example.lms_system.entity.Course;
import com.example.lms_system.entity.User;
import com.example.lms_system.service.AttendanceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = {"/attendances", "/attendances/"})
public class AttendanceController {

    private final AttendanceService service;

    @GetMapping("/all")
    public ResponseEntity<Page<Attendance>> getAttendances(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<Attendance> attendancePage = service.getAttendances(page, size);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Attendance-page-number", String.valueOf(attendancePage.getNumber()));
        headers.add("Attendance-page-size", String.valueOf(attendancePage.getSize()));
        return ResponseEntity.ok().headers(headers).body(attendancePage);
    }

    @GetMapping("/details")
    public ResponseEntity<Attendance> getAttendanceDetails(@RequestParam Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<Attendance> addAttendance(
            @RequestParam Course course,
            @RequestParam User student,
            @RequestParam(defaultValue = "false") boolean attendanceStatus,
            @RequestParam(defaultValue = "") String notes) {
        Attendance newAttendance = Attendance.builder()
                .course(course)
                .student(student)
                .attendanceStatus(attendanceStatus)
                .attendanceNote(notes)
                .build();
        service.saveAttendance(newAttendance);

        return new ResponseEntity<>(newAttendance, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Attendance> updateAttendance(
            @PathVariable Long id,
            @RequestParam Course course,
            @RequestParam User student,
            @RequestParam boolean attendanceStatus,
            @RequestParam String notes) {
        Attendance attendance = service.findById(id);
        if (attendance == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        attendance.setCourse(course);
        attendance.setStudent(student);
        attendance.setAttendanceStatus(attendanceStatus);
        attendance.setAttendanceNote(notes);
        service.saveAttendance(attendance);

        return new ResponseEntity<>(attendance, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        Attendance attendance = service.findById(id);
        if (attendance == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
