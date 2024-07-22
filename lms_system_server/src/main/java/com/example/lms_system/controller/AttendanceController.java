package com.example.lms_system.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/details/{id}")
    public ResponseEntity<Attendance> getAttendanceDetails(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/add")
    public AttendanceResponse addAttendance(@RequestBody AttendanceRequest request) {
        return service.insertAttendance(request);
    }

    @PutMapping("/update/{id}")
    public AttendanceResponse updateAttendance(@RequestBody AttendanceRequest request) {
        return service.updateAttendance(request);
    }

    @PostMapping("/status/change/{id}")
    public ResponseEntity<Void> changeAttendanceStatus(@PathVariable Long id) {
        Attendance attendance = service.findById(id);
        if (attendance == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        service.changeAttendanceStatus(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
