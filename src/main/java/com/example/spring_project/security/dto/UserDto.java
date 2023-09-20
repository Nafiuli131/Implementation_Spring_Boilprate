package com.example.spring_project.security.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserDto {
    private String userName;
    private String password;
    private String email;
    private Set<String> roleSet;
}