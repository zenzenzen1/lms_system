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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.lms_system.dto.request.ScheduleRequest;
import com.example.lms_system.entity.Schedule;
import com.example.lms_system.repository.RoomRepository;
import com.example.lms_system.repository.ScheduleRepository;
import com.example.lms_system.repository.SemesterRepository;
import com.example.lms_system.repository.SlotRepository;
import com.example.lms_system.repository.SubjectRepository;
import com.example.lms_system.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final SemesterRepository semesterRepository;
    private final SubjectRepository subjectRepository;
    private final SlotRepository slotRepository;
    private final RoomRepository roomRepository;

    public Set<Map<String, Object>> getScheduleByStudentId(String studentId, LocalDate startDate, LocalDate endDate) {
        // var schedules = scheduleRepository.findAll().stream().filter(t -> t.get)

        return scheduleRepository.getScheduleByStudentId(studentId, startDate, endDate);
    }

    public ResponseEntity<Schedule> saveSchedule(ScheduleRequest scheduleRequest) {
        System.out.println(scheduleRequest);
        if (scheduleRequest.getStudentIds() == null
                || scheduleRequest.getStudentIds().isEmpty()) {
            // validate
            return null;
        }

        var students = userRepository.findAllById(scheduleRequest.getStudentIds());

        var slot = slotRepository.findById(scheduleRequest.getSlotId());
        var semester = semesterRepository.findById(scheduleRequest.getSubjectCode());
        var subject = subjectRepository.findById(scheduleRequest.getSubjectCode());
        var room = roomRepository.findById(scheduleRequest.getRoomId());

        if (slot.isPresent() && semester.isPresent() && subject.isPresent() && room.isPresent()) {
            for (var i = semester.get().getStartDate();
                    i.isBefore(semester.get().getEndDate());
                    i = i.plusDays(1)) {

                //
                break;
            }
        }

        // validate
        return null;
    }

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
