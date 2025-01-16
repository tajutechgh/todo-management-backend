package com.tajutechgh.todobackend.controller;

import com.tajutechgh.todobackend.dto.LoginDto;
import com.tajutechgh.todobackend.dto.RegisterDto;
import com.tajutechgh.todobackend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // TODO: register user
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {

        String response = authService.registerUser(registerDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // TODO: login user
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {

        String response = authService.loginUser(loginDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
