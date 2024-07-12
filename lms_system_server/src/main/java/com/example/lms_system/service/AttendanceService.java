package com.example.lms_system.service;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.lms_system.entity.Attendance;
import com.example.lms_system.repository.AttendanceRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    public void saveAttendance(Attendance attendance) {
        attendanceRepository.save(attendance);
    }

    public void deleteById(Long id) {
        attendanceRepository.deleteById(id);
    }

    public Attendance findById(Long id) {
        return attendanceRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Attendance not found."));
    }

    public Page<Attendance> getAttendances(int page, int size) {
        List<Attendance> list = attendanceRepository.findAll();

        Pageable pageRequest = createPageRequest(page, size);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), list.size());

        List<Attendance> pageContent = list.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, list.size());
    }

    private Pageable createPageRequest(int page, int size) {
        return PageRequest.of(page, size);
    }

    public List<Attendance> getAttendanceListFromPage(int page, int size) {
        Pageable pageRequest = createPageRequest(page, size);
        Page<Attendance> attendanceList = attendanceRepository.findAll(pageRequest);

        return attendanceList.hasContent() ? attendanceList.getContent() : Collections.emptyList();
    }
}
