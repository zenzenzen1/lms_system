package com.example.lms_system.entity.key;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CourseStudentKey
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseStudentKey implements Serializable {
    @Column(name = "student_id")
    private String studentId;

    @Column(name = "course_id")
    private long courseId;
}
