package com.example.lms_system.entity;

import jakarta.persistence.PostPersist;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScheduleListener {
    @PostPersist
    public void postPersist() {
        log.info("Schedule has been created");
    }
}
