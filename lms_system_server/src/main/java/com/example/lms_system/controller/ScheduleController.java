package com.example.lms_system.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.lms_system.dto.request.ScheduleRequest;
import com.example.lms_system.entity.Room;
import com.example.lms_system.entity.Schedule;
import com.example.lms_system.entity.Slot;
import com.example.lms_system.entity.Subject;
import com.example.lms_system.service.ScheduleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = {"/schedules", "/schedules/"})
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    private final DateTimeFormatter dateTimeFormatter;

    @GetMapping("/teacherId/{teacherId}")
    public Object getScheduleByTeacherId(
            @PathVariable String teacherId, @RequestParam String startDate, @RequestParam String endDate) {
        try {
            LocalDate start = LocalDate.parse(startDate, dateTimeFormatter);
            LocalDate end = LocalDate.parse(endDate, dateTimeFormatter);
            System.out.println(start + " " + end);
            // return scheduleService.getScheduleByTeacherId(teacherId, start, end);
            return null;
        } catch (Exception e) {
            throw new RuntimeException("date parse error");
        }
    }

    @GetMapping({"/studentId/{studentId}"})
    public Object getScheduleByStudentId(
            @PathVariable String studentId, @RequestParam String startDate, @RequestParam String endDate) {
        var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-M-d").withLocale(Locale.CHINA);
        LocalDate start = LocalDate.parse(startDate, dateTimeFormatter);
        LocalDate end = LocalDate.parse(endDate, dateTimeFormatter);
        System.out.println(
                start.format(dateTimeFormatter) + " " + end.format(dateTimeFormatter) + " " + start + " " + end);
        return scheduleService.getScheduleByStudentId(studentId, start, end);
    }

    @GetMapping("/all")
    public Object getSchedules(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10000000") int size) {
        Page<Schedule> schedulePage = scheduleService.getSchedules(page, size);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Schedule-page-number", String.valueOf(schedulePage.getNumber()));
        headers.add("Schedule-page-size", String.valueOf(schedulePage.getSize()));
        return ResponseEntity.ok().headers(headers).body(schedulePage);
        // return ResponseEntity.ok().body(scheduleService.getSchedules());
    }

    @GetMapping("/details")
    public ResponseEntity<Schedule> getScheduleDetails(@RequestParam Long id) {
        return ResponseEntity.ok(scheduleService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<Set<Schedule>> addSchedules(@RequestBody ScheduleRequest scheduleRequest) {
        return ResponseEntity.ok().body(scheduleService.saveSchedules(scheduleRequest));
    }

    @PostMapping("/add")
    public ResponseEntity<Schedule> addSchedule(
            @RequestParam LocalDate trainingDate,
            @RequestParam Subject subject,
            @RequestParam Room room,
            @RequestParam Slot slot) {
        Schedule schedule = Schedule.builder()
                .trainingDate(trainingDate)
                .subject(subject)
                .room(room)
                .slot(slot)
                .build();
        scheduleService.saveSchedule(schedule);
        return ResponseEntity.ok(schedule);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Schedule> updateSchedule(
            @PathVariable Long id,
            @RequestParam LocalDate trainingDate,
            @RequestParam Subject subject,
            @RequestParam Room room,
            @RequestParam Slot slot) {
        Schedule schedule = scheduleService.findById(id);
        if (schedule == null) return ResponseEntity.notFound().build();

        schedule.setTrainingDate(trainingDate);
        schedule.setSubject(subject);
        schedule.setRoom(room);
        schedule.setSlot(slot);
        scheduleService.saveSchedule(schedule);
        return ResponseEntity.ok(schedule);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        Schedule schedule = scheduleService.findById(id);
        if (schedule == null) return ResponseEntity.notFound().build();
        scheduleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
