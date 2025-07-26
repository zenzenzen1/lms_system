package com.example.schedule_service.controller;

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

import com.example.schedule_service.entity.Semester;
import com.example.schedule_service.service.SemesterService;

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

    @PostMapping()
    public ResponseEntity<Semester> addSemester(@RequestBody Semester semester) {
        semesterService.saveSemester(semester);
        return new ResponseEntity<>(semester, HttpStatus.OK);
    }

    @PutMapping("/update/{semesterCode}")
    public ResponseEntity<Semester> updateSemester(
            @RequestParam String semesterCode,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(defaultValue = "") String description) {
        Semester semester = semesterService.findById(semesterCode);
        if (semester == null) return ResponseEntity.notFound().build();
        semester.setStartDate(startDate);
        semester.setEndDate(endDate);
        semester.setDescription(description);
        semesterService.saveSemester(semester);
        return new ResponseEntity<>(semester, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{semesterCode}")
    public ResponseEntity<Void> deleteSemester(@RequestParam String semesterCode) {
        Semester semester = semesterService.findById(semesterCode);
        if (semester == null) return ResponseEntity.notFound().build();
        semesterService.deleteById(semesterCode);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
