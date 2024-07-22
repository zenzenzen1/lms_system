package com.example.lms_system.dto.request;

import java.time.LocalDate;

import com.example.lms_system.entity.Course;
import com.example.lms_system.entity.Room;
import com.example.lms_system.entity.Slot;
import com.example.lms_system.entity.Subject;

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
public class ScheduleRequest {
    long scheduleId;
    LocalDate trainingDate;
    Subject subject;
    Room room;
    Slot slot;
    Course course;
}
