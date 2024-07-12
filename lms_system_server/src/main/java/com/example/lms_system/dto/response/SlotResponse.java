package com.example.lms_system.dto.response;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SlotResponse {
    Long slotId;

    LocalTime startTime;
    LocalTime endTime;
}
