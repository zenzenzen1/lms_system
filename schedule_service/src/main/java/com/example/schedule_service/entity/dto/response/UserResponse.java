package com.example.schedule_service.entity.dto.response;

import java.time.LocalDate;

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
public class UserResponse {
    String id;
    String userId;
    String fullName;
    String email;
    String phoneNumber;
    // boolean active;
    LocalDate dob;
    
    // Set<CourseStudent> courseStudents;
}
