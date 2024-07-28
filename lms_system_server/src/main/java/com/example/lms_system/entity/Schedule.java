package com.example.lms_system.entity;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
// @EntityListeners(ScheduleListener.class)
@Table
public class Schedule implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long scheduleId;

    LocalDate trainingDate;

    @ManyToOne
    @JoinColumn(name = "subject_code", referencedColumnName = "subjectCode")
    // @JsonBackReference)
    Subject subject;

    @ManyToOne
    @JoinColumn(name = "roomId", referencedColumnName = "roomId")
    Room room;

    @ManyToOne
    @JoinColumn(name = "slotId", referencedColumnName = "slotId")
    Slot slot;

    // @OneToMany
    // @JoinColumn(name = "attendanceId", referencedColumnName = "schedule")
    // Set<Attendance> attendances;

    @ManyToOne
    @JoinColumn(name = "courseId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Course course;
}
