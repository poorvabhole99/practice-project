package com.practice.practiceProject.repository;

import com.practice.practiceProject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "select * from user where email_id = ?1 and is_active = 1", nativeQuery = true)
    Optional<User> findByEmailIdAndIsActive(String emailId);

    Optional<User> findByEmailId(String emailId);
}
