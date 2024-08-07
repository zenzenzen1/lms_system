package com.example.lms_notification_service.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "Notification")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Notification {
    @Id
    private String id;

    private String userId;
    private String message;
    private LocalDateTime createdAt;
}
