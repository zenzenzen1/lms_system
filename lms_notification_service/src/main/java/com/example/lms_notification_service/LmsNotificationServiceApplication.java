package com.example.lms_notification_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.Lifecycle;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.stereotype.Component;

import com.example.lms_notification_service.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@SpringBootApplication()
@EnableKafka
@EnableMongoRepositories
public class LmsNotificationServiceApplication {

    @Component
    @RequiredArgsConstructor
    static class ApplicationLifecycle implements Lifecycle {
        @SuppressWarnings("unused")
        private final NotificationRepository notificationRepository;

        @Override
        public void start() {
            System.out.println("Application started");
        }

        @Override
        public void stop() {
            // notificationRepository.deleteAll();
            System.out.println("Application stopped");
        }

        @Override
        public boolean isRunning() {
            return true;
        }
    }

    public static void main(String[] args) {

        SpringApplication.run(LmsNotificationServiceApplication.class, args);
    }
}
