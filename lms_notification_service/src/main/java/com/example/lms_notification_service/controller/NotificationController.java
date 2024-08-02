package com.example.lms_notification_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lms_notification_service.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    
    @GetMapping("/getNotificationsByUserId/{userId}")
    public Object getNotificationsByUserId(@PathVariable String userId){  
        return notificationService.getNotificationsByUserId(userId);
    }
}
