package com.example.schedule_service.entity.dto.response;

import com.example.schedule_service.entity.Schedule;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceResponse {
    Long attendanceId;
    Boolean attendanceStatus;
    String attendanceNote;
    UserResponse student;
    
    Schedule schedule;
}
