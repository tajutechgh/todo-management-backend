package com.tajutechgh.todobackend.controller;

import com.tajutechgh.todobackend.dto.UserDto;
import com.tajutechgh.todobackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/auth/user")
public class UserController {
    
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // TODO: get current user profile
    @GetMapping("/profile")
    public ResponseEntity<UserDto> getCurrentUser(Authentication authentication) {

        String username = authentication.getName();
        
        UserDto userDto = userService.getCurrentUserProfile(username);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
