package com.example.schedule_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Subject {
    // @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    // long subjectId;
    @Id
    String subjectCode; // SWP

    String subjectName; // softwareprojet

    @Default
    boolean status = true;
}
