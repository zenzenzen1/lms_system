package com.example.lms_system.configuration;

import java.time.LocalTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.lms_system.entity.Schedule;
import com.example.lms_system.entity.Slot;
import com.example.lms_system.entity.Subject;
import com.example.lms_system.repository.ScheduleRepository;
import com.example.lms_system.repository.SlotRepository;
import com.example.lms_system.repository.SubjectRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AppInit {
    private final ScheduleRepository scheduleRepository;

    private final SubjectRepository subjectRepository;

    private final SlotRepository slotRepository;

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            Subject subject1 = Subject.builder()
                    .subjectCode("SWP")
                    .subjectName("software project")
                    .build();
            Subject subject2 = Subject.builder()
                    .subjectCode("SWT")
                    .subjectName("software testing")
                    .build();
            subjectRepository.saveAll(List.of(subject1, subject2));

            scheduleRepository.saveAll(List.of(
                    Schedule.builder().subject(subject1).build(),
                    Schedule.builder().subject(subject2).build()));
            var slots = List.of(
                    Slot.builder()
                            .startTime(LocalTime.of(7, 30, 0))
                            .endTime(LocalTime.of(9, 0, 0))
                            .build(),
                    Slot.builder()
                            .startTime(LocalTime.of(9, 10, 0))
                            .endTime(LocalTime.of(10, 40, 0))
                            .build());
            slotRepository.saveAll(slots);
        };
    }
}
