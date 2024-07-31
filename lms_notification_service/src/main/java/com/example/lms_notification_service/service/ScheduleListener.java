package com.example.lms_notification_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.event.dto.lms.ScheduleStudent;
import com.example.lms_notification_service.entity.Notification;
import com.example.lms_notification_service.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleListener {
    private final NotificationRepository notificationRepository;
    
    @Value("${saveSchedulesForStudentdefaultMessage}")
    private String saveSchedulesForStudentdefaultMessage;
    
    @KafkaListener(groupId = "lms-notification-group", id = "create-schedule", topics = {"create-schedule"})
    public void listen(ScheduleStudent scheduleStudent) {
        notificationRepository.save(Notification.builder().createdAt(scheduleStudent.getCreatedAt()).userId(scheduleStudent.getStudentId()).message(saveSchedulesForStudentdefaultMessage).build());
        log.info("Received message from schedule service: {}", scheduleStudent); 
    }
}
