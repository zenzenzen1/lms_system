package com.example.lms_notification_service.configuration;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.lms_notification_service.entity.Notification;
import com.example.lms_notification_service.repository.NotificationRepository;
import com.example.lms_system.entity.Attendance;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class AppInit {
    @Bean
    CommandLineRunner commandLineRunner(NotificationRepository notificationRepository) {
        return args -> {
            if (notificationRepository.findAll().stream().filter(t -> t.getUserId().equals("1")).count() == 0)
                notificationRepository.save(Notification.builder()
                        .userId("1")
                        .message("Test if mongodb is successed")
                        .createdAt(LocalDateTime.now())
                        .build());
            Attendance attendance = Attendance.builder().attendanceId(0).attendanceNote("test").build();
            log.error("Attendance: {}", attendance);
            log.error("if you see this message, dont worry, it means you imported lms_system_server service successfully");
        };
    }
}
