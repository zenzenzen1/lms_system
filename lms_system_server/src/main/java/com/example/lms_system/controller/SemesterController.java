package com.example.lms_system.controller;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.lms_system.dto.request.SemesterRequest;
import com.example.lms_system.dto.response.SemesterResponse;
import com.example.lms_system.entity.Semester;
import com.example.lms_system.service.SemesterService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = {"/semesters", "/semesters/"})
public class SemesterController {

    private final SemesterService semesterService;

    @GetMapping()
    public Set<Semester> getAllSemesters() {
        return semesterService.getAllSemesters();
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Semester>> getSemesters(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<Semester> semesterPage = semesterService.getSemesters(page, size);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Semester-page-number", String.valueOf(semesterPage.getNumber()));
        headers.add("Semester-page-size", String.valueOf(semesterPage.getSize()));
        return ResponseEntity.ok().headers(headers).body(semesterPage);
    }

    @GetMapping("/details")
    public ResponseEntity<Semester> getSemesterDetails(@RequestParam String id) {
        return ResponseEntity.ok(semesterService.findById(id));
    }

    @PostMapping("/add")
    public SemesterResponse addSemester(@RequestBody SemesterRequest request) {
        return semesterService.insertSemester(request);
    }

    @PutMapping("/update/{semesterCode}")
    public SemesterResponse updateSemester(@RequestBody SemesterRequest request) {
        return semesterService.updateSemester(request);
    }

    @DeleteMapping("/delete/{semesterCode}")
    public ResponseEntity<Void> deleteSemester(@RequestParam String semesterCode) {
        Semester semester = semesterService.findById(semesterCode);
        if (semester == null) return ResponseEntity.notFound().build();
        semesterService.deleteById(semesterCode);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
