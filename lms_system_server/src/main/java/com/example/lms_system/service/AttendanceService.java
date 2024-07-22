package com.example.lms_system.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.lms_system.dto.request.AttendanceRequest;
import com.example.lms_system.dto.response.AttendanceResponse;
import com.example.lms_system.entity.Attendance;
import com.example.lms_system.mapper.AttendanceMapper;
import com.example.lms_system.repository.AttendanceRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;

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

    public AttendanceResponse insertAttendance(AttendanceRequest request) {
        var attendance = attendanceMapper.toAttendance(request);
        return attendanceMapper.toAttendanceResponse(attendanceRepository.save(attendance));
    }

    public AttendanceResponse updateAttendance(AttendanceRequest request) {
        var target = attendanceMapper.toAttendance(request);
        var attendance = attendanceRepository
                        .findById(target.getAttendanceId())
                        .orElseThrow(() -> new IllegalArgumentException("Attendance not found"));
        attendance = attendanceMapper.toAttendance(request);
        return attendanceMapper.toAttendanceResponse(attendanceRepository.save(attendance));
    }

    public void changeAttendanceStatus(long id) {
        Attendance attendance = findById(id);
        if (attendance == null) throw new IllegalArgumentException("Attendance not found");
        attendance.setAttendanceStatus(!attendance.isAttendanceStatus());
        attendanceRepository.save(attendance);
    }

    public List<AttendanceResponse> insertMultipleAttendances(List<AttendanceRequest> requests) {
        return requests.stream()
                        .map(this::insertAttendance)
                        .collect(Collectors.toList());
    }
}
