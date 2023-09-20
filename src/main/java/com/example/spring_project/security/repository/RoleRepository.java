package com.example.spring_project.security.repository;

import com.example.spring_project.security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByRoleName(String role);
}
