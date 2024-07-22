package com.example.lms_system.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.lms_system.dto.request.SubjectRequest;
import com.example.lms_system.dto.response.SubjectResponse;
import com.example.lms_system.entity.Subject;
import com.example.lms_system.mapper.SubjectMapper;
import com.example.lms_system.repository.SubjectRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

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

    public Set<Subject> getAllSubject() {
        return new HashSet<>(subjectRepository.findAll());
    }

    public SubjectResponse insertSubject(SubjectRequest request) {
        Subject subject = subjectMapper.toSubject(request);
        return subjectMapper.toSubjectResponse(subjectRepository.save(subject));
    }

    public SubjectResponse updateSubject(SubjectRequest request) {
        Subject target = subjectMapper.toSubject(request);
        Subject subject = subjectRepository.findById(target.getSubjectCode()).orElseThrow(() -> new IllegalArgumentException("Subject not found"));
        subject = subjectMapper.toSubject(request);
        return subjectMapper.toSubjectResponse(subjectRepository.save(subject));
    }
}
