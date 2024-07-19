package com.example.lms_system.service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.lms_system.entity.Semester;
import com.example.lms_system.repository.SemesterRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SemesterService {

    private final SemesterRepository semesterRepository;

    public Set<Semester> getAllSemesters() {
        return semesterRepository.findAll().stream().collect(Collectors.toSet());
    }

    public void saveSemester(Semester semester) {
        semesterRepository.save(semester);
    }

    public void deleteById(String id) {
        semesterRepository.deleteById(id);
    }

    public Semester findById(String id) {
        return semesterRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Semester not found"));
    }

    public Page<Semester> getSemesters(int page, int size) {
        List<Semester> list = semesterRepository.findAll();

        Pageable pageRequest = createPageRequest(page, size);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), list.size());

        List<Semester> pageContent = list.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, list.size());
    }

    private Pageable createPageRequest(int page, int size) {
        return PageRequest.of(page, size);
    }

    public List<Semester> getSemesterListFromPage(int page, int size) {
        Pageable pageRequest = createPageRequest(page, size);
        Page<Semester> SemesterList = semesterRepository.findAll(pageRequest);

        return SemesterList.hasContent() ? SemesterList.getContent() : Collections.emptyList();
    }
}
