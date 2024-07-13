package com.example.lms_system.service;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.lms_system.entity.Schedule;
import com.example.lms_system.repository.ScheduleRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public void saveSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
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
}
