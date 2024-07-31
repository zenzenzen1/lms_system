package com.example.kafka_dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ScheduleStudent implements Serializable {
    private String studentId;
    private LocalDateTime createdAt;
}
