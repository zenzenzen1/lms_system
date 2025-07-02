package com.example.schedule_service.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.schedule_service.entity.User;



@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query(
            value =
                    "select * from users where id in (select student_id from attendance where schedule_id = :scheduleId)",
            nativeQuery = true)
    Set<User> getStudentsByScheduleId(long scheduleId);

    Optional<User> findByUserId(String userId);

    Optional<User> findByEmail(String email);
}
