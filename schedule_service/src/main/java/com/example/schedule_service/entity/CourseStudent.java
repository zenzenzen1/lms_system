package com.example.schedule_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "student_id", "course_id" }) })
public class CourseStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long courseStudentId;

    // @EmbeddedId
    // CourseStudentKey id;

    // @ManyToOne()
    // @MapsId("studentId")
    // @JoinColumn(name = "student_id")
    // User student;
    String studentId;

    @ManyToOne()
    @JoinColumn(name = "course_id")
    Course course;

    @Default
    boolean status = true;
}
