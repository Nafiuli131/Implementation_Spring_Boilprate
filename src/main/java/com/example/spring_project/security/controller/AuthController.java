package com.example.spring_project.security.controller;

import com.example.spring_project.security.dto.JwtRequest;
import com.example.spring_project.security.dto.JwtResponse;
import com.example.spring_project.security.dto.UserDto;
import com.example.spring_project.security.entity.Role;
import com.example.spring_project.security.helper.JwtTokenUtil;
import com.example.spring_project.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;



    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserDto userDTO) {
        UserDto newUser = userService.signUp(userDTO);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> signIn(@RequestBody JwtRequest jwtRequest) {
        String userEmail = jwtRequest.getUserEmail();
        String password = jwtRequest.getPassword();
        String token = userService.signIn(userEmail, password);
        UserDto userDetails = userService.findByUserEmail(userEmail);
        return ResponseEntity.ok(new JwtResponse(token, userDetails));
    }
}
