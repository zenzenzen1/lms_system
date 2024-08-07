package com.example.lms_notification_service.controller;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lms_notification_service.entity.MessageDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping()
    public MessageDTO postMethodName(@RequestBody MessageDTO messageDTO) {
        kafkaTemplate.send("notification-message", messageDTO);
        return messageDTO;
    }
}
