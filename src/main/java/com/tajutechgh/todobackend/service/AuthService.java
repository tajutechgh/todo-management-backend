package com.tajutechgh.todobackend.service;

import com.tajutechgh.todobackend.dto.JwtAuthResponse;
import com.tajutechgh.todobackend.dto.LoginDto;
import com.tajutechgh.todobackend.dto.RegisterDto;

public interface AuthService {

    String registerUser(RegisterDto registerDto);

    JwtAuthResponse loginUser(LoginDto loginDto);
}
