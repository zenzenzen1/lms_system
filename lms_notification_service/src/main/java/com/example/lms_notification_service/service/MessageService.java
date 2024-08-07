package com.example.lms_notification_service.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.lms_notification_service.entity.MessageDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {
    @SuppressWarnings("unused")
    private final EmailService emailService;

    @KafkaListener(groupId = "lms-notification-group", id = "notification-message", topics = "notification-message")
    public void listen(MessageDTO messageDTO) {
        log.info("Received message: {}", messageDTO);
        emailService.sendEmail(messageDTO);
    }

    // @KafkaListener(topics = "create-schedule")
    // public void listenCreateSchedule(ScheduleStudent scheduleStudent) {
    //     log.info("Received message from schedule service: {}", scheduleStudent);
    //     // emailService.sendEmail(messageDTO);
    // }

}
