package com.example.schedule_service.controller;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.schedule_service.entity.Subject;
import com.example.schedule_service.entity.dto.response.ApiResponse;
import com.example.schedule_service.service.SubjectService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = {"/subjects", "/subjects/"})
public class SubjectController {

    private final SubjectService subjectService;

    // @GetMapping()
    // public ResponseEntity<Page<Subject>> getAttendances(
    //         @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
    //     Page<Subject> subjectPage = subjectService.getSubjects(page, size);
    //     HttpHeaders headers = new HttpHeaders();
    //     headers.add("Subject-page-number", String.valueOf(subjectPage.getNumber()));
    //     headers.add("Subject-page-size", String.valueOf(subjectPage.getSize()));
    //     return ResponseEntity.ok().headers(headers).body(subjectPage);
    // }
    

    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubjectDetails(@PathVariable String id) {
        return ResponseEntity.ok(subjectService.findById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<Subject> addSubject(
            @RequestParam String subjectCode,
            @RequestParam String subjectName,
            @RequestParam(defaultValue = "false") boolean status) {
        if (subjectService.findById(subjectName) != null)
            return ResponseEntity.badRequest().build();
        Subject subject = Subject.builder()
                .subjectCode(subjectCode)
                .subjectName(subjectName)
                .status(status)
                .build();
        subjectService.saveSubject(subject);
        return ResponseEntity.ok(subject);
    }

    @PutMapping("/update/{subjectCode}")
    public ResponseEntity<Subject> updateSubject(
            @PathVariable String subjectCode,
            @RequestParam String subjectName,
            @RequestParam(defaultValue = "false") boolean status) {
        Subject subject = subjectService.findById(subjectCode);
        if (subject == null) return ResponseEntity.notFound().build();
        subject.setSubjectName(subjectName);
        subject.setStatus(status);
        subjectService.saveSubject(subject);
        return ResponseEntity.ok(subject);
    }

    @DeleteMapping("/delete/{subjectCode}")
    public ResponseEntity<Void> deleteSubject(@PathVariable String subjectCode) {
        Subject subject = subjectService.findById(subjectCode);
        if (subject == null) return ResponseEntity.notFound().build();
        subjectService.deleteById(subjectCode);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ApiResponse<Set<Subject>> getAllSubjects() {
        return ApiResponse.<Set<Subject>>builder()
                .result(subjectService.getAllSubject())
                .build();
    }
}
