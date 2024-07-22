package com.example.lms_system.dto.request;

import com.example.lms_system.entity.Semester;
import com.example.lms_system.entity.Subject;
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
public class CourseRequest {
    Long courseId;
    Subject subject;
    Semester semester;
    User teacher;
}
