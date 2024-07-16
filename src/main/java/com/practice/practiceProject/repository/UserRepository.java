package com.practice.practiceProject.repository;

import com.practice.practiceProject.entities.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  @Query(value = "select * from user where email_id = ?1 and status = 'ACTIVE'", nativeQuery = true)
  Optional<User> findByEmailIdAndIsActive(String emailId);

  @Query(value = "select * from user where email_id = ?1 and status = 'INACTIVE'", nativeQuery = true)
  Optional<User> findByEmailIdAndIsDeactivated(String emailId);

  Optional<User> findByEmailId(String emailId);

  @Query(value = "SELECT * FROM practice_project.user WHERE roles LIKE %:role%", nativeQuery = true)
  List<User> findAllUserByRole(@Param("role") String role);


}
