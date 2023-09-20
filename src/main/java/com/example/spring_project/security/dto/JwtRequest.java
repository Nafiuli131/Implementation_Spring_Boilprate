package com.example.spring_project.security.dto;

import com.example.spring_project.security.entity.Role;
import lombok.Data;

import java.util.Set;

@Data
public class JwtRequest {
    private String userEmail;
    private String password;
}
