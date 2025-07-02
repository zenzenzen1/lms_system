package com.example.schedule_service.entity.dto.response;

import com.example.schedule_service.entity.Course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseStudentResponse {
    private Course course;
    private UserResponse student;
    private Boolean status;
}
