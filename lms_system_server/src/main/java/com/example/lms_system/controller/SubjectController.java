package com.example.lms_system.controller;

import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lms_system.dto.response.ApiResponse;
import com.example.lms_system.entity.Subject;
import com.example.lms_system.service.SubjectService;

import lombok.RequiredArgsConstructor;

@RequestMapping(path = {"/subjects"})
@RestController
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @GetMapping
    public ApiResponse<Set<Subject>> getAllSubjects() {
        return ApiResponse.<Set<Subject>>builder()
                .result(subjectService.getAllSubject())
                .build();
    }
}
