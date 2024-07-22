package com.example.lms_system.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

import com.example.lms_system.entity.key.CourseStudentKey;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseStudent {

    @EmbeddedId
    CourseStudentKey id;

    @ManyToOne()
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    User student;

    @ManyToOne()
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    Course course;

    @Default
    boolean status = true;
}
