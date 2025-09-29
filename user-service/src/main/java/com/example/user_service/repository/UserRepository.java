package com.example.user_service.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.user_service.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    Optional<User> findByAccountId(String accountId);
    
    boolean existsByAccountId(String accountId);
    
    @Query("SELECT u FROM User u WHERE " +
           "(:fullName IS NULL OR LOWER(u.fullName) LIKE LOWER(CONCAT('%', :fullName, '%'))) AND " +
           "(:phoneNumber IS NULL OR u.phoneNumber LIKE %:phoneNumber%) AND " +
           "(:address IS NULL OR LOWER(u.address) LIKE LOWER(CONCAT('%', :address, '%')))")
    Page<User> findUsersWithFilters(
        @Param("fullName") String fullName,
        @Param("phoneNumber") String phoneNumber,
        @Param("address") String address,
        Pageable pageable
    );
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.accountId = :accountId AND u.id <> :userId")
    long countByAccountIdAndIdNot(@Param("accountId") String accountId, @Param("userId") Integer userId);
}