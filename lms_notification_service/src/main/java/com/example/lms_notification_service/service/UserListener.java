package com.example.lms_notification_service.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.event.dto.IdentityNotificationEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserListener {
    @KafkaListener(
            groupId = "lms-notification-group",
            id = "create-user",
            topics = {"create-user"})
    public void listen(IdentityNotificationEvent event) {
        log.info("Received message from user service: {}", event);
    }
}
