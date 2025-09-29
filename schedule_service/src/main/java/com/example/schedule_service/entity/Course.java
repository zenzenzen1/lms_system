package com.example.schedule_service.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE) @Builder @Data @NoArgsConstructor @AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long courseId;

    @ManyToOne
    @JoinColumn(name = "subject_code")
    Subject subject;

    @ManyToOne
    @JoinColumn(name = "semester_code")
    Semester semester;

    // String code;

    // @ManyToOne
    // @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    // @OnDelete(action = OnDeleteAction.CASCADE)
    // @JsonBackReference
    // User teacher;
    String teacherId;

    @OneToMany(mappedBy = "course")
    @JsonIgnore
    Set<CourseStudent> courseStudents;
}
