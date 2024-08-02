package com.example.event.dto.lms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceStatusNotification{
    private double absentPercentage;
    private String studentId;
    private String email;
    private String fullName;
}
