package com.example.lms_notification_service.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.event.dto.lms.AttendanceStatusNotification;
import com.example.lms_notification_service.entity.MessageDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceListener {
    private final EmailService emailService;
    
    @KafkaListener(id = "send-attendance-status-email", groupId = "lms-notification-group", topics = "send-email-attendance-status")
    public void sendAttendanceStatusEmail(AttendanceStatusNotification attendanceStatusNotification){
        emailService.sendEmail(MessageDTO.builder().to(attendanceStatusNotification.getEmail()).toName(attendanceStatusNotification.getFullName()).subject("Attendance Status").content("You have been absent for " + attendanceStatusNotification.getAbsentPercentage() + "% of the total allowed days.").build());
    }
}
