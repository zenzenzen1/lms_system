package com.example.lms_system.dto.response;

import java.util.Set;

import com.example.lms_system.entity.CourseStudent;
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
public class CourseResponse {
    Long courseId;
    Subject subject;
    Semester semester;
    User teacher;
    Set<CourseStudent> courseStudents;
}
