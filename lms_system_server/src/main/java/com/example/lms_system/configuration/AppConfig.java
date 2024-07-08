package com.example.lms_system.configuration;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.lms_system.entity.Schedule;
import com.example.lms_system.entity.Subject;
import com.example.lms_system.repository.ScheduleRepository;
import com.example.lms_system.repository.SubjectRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final ScheduleRepository scheduleRepository;

    private final SubjectRepository subjectRepository;

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            Subject subject1 =
                    Subject.builder().subjectCode("SWP").subjectName("name1").build();
            Subject subject2 =
                    Subject.builder().subjectCode("SWT").subjectName("name2").build();
            subjectRepository.saveAll(List.of(subject1, subject2));

            scheduleRepository.saveAll(List.of(
                    Schedule.builder().subject(subject1).build(),
                    Schedule.builder().subject(subject2).build()));
        };
    }
}
