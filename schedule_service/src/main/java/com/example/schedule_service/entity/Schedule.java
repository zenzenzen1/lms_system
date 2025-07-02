package com.example.schedule_service.entity;

import java.time.LocalDate;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
// @EntityListeners(ScheduleListener.class)
@Table
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    Long scheduleId;
    
    @EqualsAndHashCode.Include
    LocalDate trainingDate;

    @ManyToOne
    @JoinColumn(name = "subject_code", referencedColumnName = "subjectCode")
    @EqualsAndHashCode.Include
    // @JsonBackReference)
    Subject subject;

    @ManyToOne 
    @JoinColumn(name = "roomId", referencedColumnName = "roomId")
    @EqualsAndHashCode.Include
    Room room;

    @ManyToOne
    @JoinColumn(name = "slotId", referencedColumnName = "slotId")
    @EqualsAndHashCode.Include
    Slot slot;

    // @OneToMany
    // @JoinColumn(name = "attendanceId", referencedColumnName = "schedule")
    // Set<Attendance> attendances;

    @ManyToOne
    @JoinColumn(name = "courseId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Course course;
}
