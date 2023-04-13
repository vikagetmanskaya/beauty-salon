package com.example.diploma.repository;

import com.example.diploma.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByUsername(String username);
    public User findByEmailIgnoreCase(String email);
    Boolean existsByEmail(String email);
}
