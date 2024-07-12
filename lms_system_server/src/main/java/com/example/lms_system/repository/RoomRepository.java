package com.example.lms_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.lms_system.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {}
