package com.example.lms_notification_service.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks;

@Service
public class SseService {

    private final Map<String, Sinks.Many<String>> userSinks = new ConcurrentHashMap<>();

    public Sinks.Many<String> createSinkForUser(String userId) {
        Sinks.Many<String> sink = Sinks.many().multicast().onBackpressureBuffer();
        userSinks.put(userId, sink);
        return sink;
    }

    public void removeSink(String userId) {
        userSinks.remove(userId);
    }

    public void sendToUser(String userId, String message) {
        Sinks.Many<String> sink = userSinks.get(userId);
        if (sink != null) {
            sink.tryEmitNext(message);
        }
    }
}