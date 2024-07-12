package com.example.lms_system.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.lms_system.entity.Subject;
import com.example.lms_system.repository.SubjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public Set<Subject> getAllSubject() {
        return new HashSet<>(subjectRepository.findAll());
    }
}
