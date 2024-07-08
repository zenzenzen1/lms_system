// package com.example.lms_system.entity;

// import java.util.Set;

// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.ManyToMany;

// import lombok.AccessLevel;
// import lombok.Builder;
// import lombok.Data;
// import lombok.experimental.FieldDefaults;

// @Data
// @Builder
// @FieldDefaults(level = AccessLevel.PRIVATE)
// @Entity
// public class Role {
//     @Id
//     @GeneratedValue(strategy = GenerationType.AUTO)
//     long roleId;

//     String roleName;

//     @ManyToMany(mappedBy = "roles")
//     Set<User> users;
// }
