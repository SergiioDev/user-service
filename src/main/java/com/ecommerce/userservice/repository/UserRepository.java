package com.ecommerce.userservice.repository;

import com.ecommerce.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM users u where u.email = :email")
    Optional<User> findByEmail(String email);
}