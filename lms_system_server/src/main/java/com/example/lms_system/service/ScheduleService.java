package com.example.lms_system.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.lms_system.dto.request.ScheduleRequest;
import com.example.lms_system.dto.response.ScheduleResponse;
import com.example.lms_system.entity.Schedule;
import com.example.lms_system.mapper.ScheduleMapper;
import com.example.lms_system.repository.ScheduleRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;

    public Set<Map<String, Object>> getScheduleByStudentId(String studentId, LocalDate startDate, LocalDate endDate) {
        // var schedules = scheduleRepository.findAll().stream().filter(t -> t.get)

        return scheduleRepository.getScheduleByStudentId(studentId, startDate, endDate);
    }

    public void deleteById(Long id) {
        scheduleRepository.deleteById(id);
    }

    public Schedule findById(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Schedule not found"));
    }

    public Page<Schedule> getSchedules(int page, int size) {
        List<Schedule> list = scheduleRepository.findAll();

        Pageable pageRequest = createPageRequest(page, size);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), list.size());

        List<Schedule> pageContent = list.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, list.size());
    }

    private Pageable createPageRequest(int page, int size) {
        return PageRequest.of(page, size);
    }

    public List<Schedule> getScheduleListFromPage(int page, int size) {
        Pageable pageRequest = createPageRequest(page, size);
        Page<Schedule> scheduleList = scheduleRepository.findAll(pageRequest);

        return scheduleList.hasContent() ? scheduleList.getContent() : Collections.emptyList();
    }

    public ScheduleResponse insertSchedule(ScheduleRequest request) {
        var schedule = scheduleMapper.toSchedule(request);
        return scheduleMapper.toScheduleResponse(scheduleRepository.save(schedule));
    }

    public ScheduleResponse updateSchedule(ScheduleRequest request) {
        var target = scheduleMapper.toSchedule(request);
        var schedule = scheduleRepository.findById(target.getScheduleId()).orElseThrow(() -> new IllegalArgumentException("Schedule not found"));
        schedule = scheduleMapper.toSchedule(request);
        return scheduleMapper.toScheduleResponse(scheduleRepository.save(schedule));
    }
}
