package com.example.lms_system.dto.response;

import com.example.lms_system.entity.Schedule;
import com.example.lms_system.entity.User;

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
    long attendanceId;
    User student;
    Schedule schedule;
    boolean attendanceStatus;
    String attendanceNote;
}
