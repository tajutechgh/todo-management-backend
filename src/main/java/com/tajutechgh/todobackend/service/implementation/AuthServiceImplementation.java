package com.tajutechgh.todobackend.service.implementation;

import com.tajutechgh.todobackend.dto.LoginDto;
import com.tajutechgh.todobackend.dto.RegisterDto;
import com.tajutechgh.todobackend.entity.Role;
import com.tajutechgh.todobackend.entity.User;
import com.tajutechgh.todobackend.exception.TodoAPIException;
import com.tajutechgh.todobackend.repository.RoleRepository;
import com.tajutechgh.todobackend.repository.UserRepository;
import com.tajutechgh.todobackend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImplementation implements AuthService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    public AuthServiceImplementation(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String registerUser(RegisterDto registerDto) {

        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "User already exist with email " + registerDto.getEmail());
        }

        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "User already exist with username " + registerDto.getUsername());
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());

        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByName("USER");

        roles.add(userRole);

        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully";
    }

    @Override
    public String loginUser(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "user logged in successfully";
    }
}
