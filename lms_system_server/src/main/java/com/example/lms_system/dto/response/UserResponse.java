package com.example.lms_system.dto.response;

import java.time.LocalDate;
import java.util.Set;

import com.example.lms_system.entity.CourseStudent;

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
    String userId;
    String fullname;
    String email;
    String phoneNumber;
    boolean active;
    LocalDate dob;
    Set<CourseStudent> courseStudents;
}
