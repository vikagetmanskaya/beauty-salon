package com.example.diploma.repository;

import com.example.diploma.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    public Role getByName(String name);
}
