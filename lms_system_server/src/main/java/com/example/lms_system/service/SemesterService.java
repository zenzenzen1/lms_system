package com.example.lms_system.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.lms_system.dto.request.SemesterRequest;
import com.example.lms_system.dto.response.SemesterResponse;
import com.example.lms_system.entity.Semester;
import com.example.lms_system.mapper.SemesterMapper;
import com.example.lms_system.repository.SemesterRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SemesterService {

    private final SemesterRepository semesterRepository;
    private final SemesterMapper semesterMapper;

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

    public SemesterResponse insertSemester(SemesterRequest request) {
        var semester = semesterMapper.toSemester(request);
        return semesterMapper.toSemesterResponse(semesterRepository.save(semester));
    }

    public SemesterResponse updateSemester(SemesterRequest request) {
        var target = semesterMapper.toSemester(request);
        var semester = semesterRepository.findById(target.getSemesterCode()).orElseThrow(() -> new IllegalArgumentException("Semester not found"));
        semester = semesterMapper.toSemester(request);
        return semesterMapper.toSemesterResponse(semesterRepository.save(semester));
    }

    public Semester getSemesterByDate(LocalDate date) {
        return (Semester)semesterRepository.findAll()
                                            .stream()
                                            .filter(s -> s.getStartDate().isBefore(date) && s.getEndDate().isAfter(date))
                                            .findFirst()
                                            .orElseThrow(() -> new IllegalArgumentException("Date not in any semester"));        
    }
}
