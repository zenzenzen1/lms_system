package com.example.lms_system.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * CourseStudentKey
 */
@Embeddable
public class CourseStudentKey implements Serializable {
    @Column(name = "student_id")
    private String studentId;

    @Column(name = "course_id")
    private long courseId;
}
