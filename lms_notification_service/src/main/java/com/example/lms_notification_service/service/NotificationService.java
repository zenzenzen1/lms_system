package com.example.lms_notification_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.lms_notification_service.entity.Notification;
import com.example.lms_notification_service.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public List<Notification> getNotificationsByUserId(String userId) {
        return notificationRepository.findByUserId(userId);
    }
}
