package com.example.schedule_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.schedule_service.entity.Room;



@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {}
