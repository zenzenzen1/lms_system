package com.example.schedule_service.entity;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long slotId;

    LocalTime startTime;
    LocalTime endTime;
}
