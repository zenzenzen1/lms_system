package com.example.schedule_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.schedule_service.entity.Slot;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {}
