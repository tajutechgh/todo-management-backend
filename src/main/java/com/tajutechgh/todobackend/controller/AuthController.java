package com.tajutechgh.todobackend.controller;

import com.tajutechgh.todobackend.dto.JwtAuthResponse;
import com.tajutechgh.todobackend.dto.LoginDto;
import com.tajutechgh.todobackend.dto.RegisterDto;
import com.tajutechgh.todobackend.dto.UserDto;
import com.tajutechgh.todobackend.entity.Role;
import com.tajutechgh.todobackend.entity.User;
import com.tajutechgh.todobackend.repository.UserRepository;
import com.tajutechgh.todobackend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;
    private UserRepository userRepository;

    public AuthController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    // TODO: register user
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {

        String response = authService.registerUser(registerDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // TODO: login user
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {

        JwtAuthResponse jwtAuthResponse = authService.loginUser(loginDto);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }


    // TODO: get current user details
    @GetMapping("/current-user")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {

        String username = authentication.getName();

        System.out.println("current username = " +username);

        User user = userRepository.findByUsername(username).orElseThrow(

                () -> new UsernameNotFoundException("Current user not found")
        );

        String roleString = user.getRoles().stream().map(Role::getName).collect(Collectors.joining(", "));

        UserDto currentUser = new UserDto(
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                roleString
        );

        return ResponseEntity.ok(currentUser);
    }
}
