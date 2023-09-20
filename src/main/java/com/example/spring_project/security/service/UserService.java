package com.example.spring_project.security.service;

import com.example.spring_project.exception.ApiExceptionHandler;
import com.example.spring_project.exception.BadExceptionHandler;
import com.example.spring_project.exception.ResourceNotFoundExceptionHandler;
import com.example.spring_project.security.config.SecurityConfig;
import com.example.spring_project.security.dto.UserDto;
import com.example.spring_project.security.entity.Role;
import com.example.spring_project.security.entity.User;
import com.example.spring_project.security.helper.JwtTokenUtil;
import com.example.spring_project.security.repository.RoleRepository;
import com.example.spring_project.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RoleRepository roleRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    public UserDto signUp(UserDto userDto) {

        Optional<User> checkUser = userRepository.findByEmail(userDto.getEmail());
        if(checkUser.isPresent()){
            throw new  BadExceptionHandler("Same user exists");
        }
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        Set<Role> roles = new HashSet<>();
        for (String roleName : userDto.getRoleSet()) {
           Role role = roleRepository.findByRoleName(roleName);
            if (role != null) {
                roles.add(role);
            }
        }

        user.setRoles(roles);
        userRepository.save(user);
        return userDto;
    }

    public String signIn(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundExceptionHandler("User not found"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResourceNotFoundExceptionHandler("Invalid password");
        }
        return jwtTokenUtil.generateToken(user.getUserName(), user.getRoles(), user.getEmail(),user.getId());
    }


    public UserDto findByUserEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        UserDto userDTO = new UserDto();
        userDTO.setUserName(user.getUserName());
        Set<String> roleNames = user.getRoles()
                .stream()
                .map(Role::getRoleName)
                .collect(Collectors.toSet());

        userDTO.setRoleSet(roleNames);
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
}
