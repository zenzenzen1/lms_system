package com.example.lms_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lms_system.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {}
