package com.example.lms_system.service;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.lms_system.entity.*;
import com.example.lms_system.repository.SubjectRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public void saveSubject(Subject subject) {
        subjectRepository.save(subject);
    }

    public void deleteById(String id) {
        subjectRepository.deleteById(id);
    }

    public Subject findById(String id) {
        return subjectRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Subject not found"));
    }

    private Pageable createPageRequest(int page, int size) {
        return PageRequest.of(page, size);
    }

    public Page<Subject> getSubjects(int page, int size) {
        List<Subject> subjects = subjectRepository.findAll();

        Pageable pageRequest = createPageRequest(page, size);

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), subjects.size());

        List<Subject> pageContent = subjects.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, subjects.size());
    }

    public List<Subject> getSubjectListFromPage(int page, int size) {
        Pageable pageRequest = createPageRequest(page, size);
        Page<Subject> subjects = subjectRepository.findAll(pageRequest);

        return subjects.hasContent() ? subjects.getContent() : Collections.emptyList();
    }
}
