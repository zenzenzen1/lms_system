package com.example.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class AttendanceStatusNotification {
    private double absentPercentage;
    private String studentId;
    private String email;
    private String fullName;
}
