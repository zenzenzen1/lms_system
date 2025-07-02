package com.example.schedule_service.entity;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    // @Column(name = "user_id")
    String id;

    // identity_service
    String userId;

    String fullName;

    @Column(unique = true)
    String email;

    String phoneNumber;

    LocalDate dob;

    // roles
    // @ManyToMany
    // @JoinTable(joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
    // Set<Role> roles;
    
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Set<CourseStudent> courseStudents;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "class_code")
    Class _class;
}
