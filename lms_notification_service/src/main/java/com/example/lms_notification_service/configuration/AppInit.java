package com.example.lms_notification_service.configuration;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.lms_notification_service.entity.Notification;
import com.example.lms_notification_service.repository.NotificationRepository;

@Configuration
public class AppInit {
    @Bean
    CommandLineRunner commandLineRunner(NotificationRepository notificationRepository) {
        return args -> {
            notificationRepository.save(Notification.builder()
                    .userId("1")
                    .message("Test if mongodb is successed")
                    .createdAt(LocalDateTime.now())
                    .build());
        };
    }
}
 