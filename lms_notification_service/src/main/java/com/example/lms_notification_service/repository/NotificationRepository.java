package com.example.lms_notification_service.repository;

import org.springframework.stereotype.Repository;

import com.example.lms_notification_service.entity.Notification;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
    @Query("{ 'userId' : ?0 }")
    public List<Notification> findByUserId(String userId);
}
