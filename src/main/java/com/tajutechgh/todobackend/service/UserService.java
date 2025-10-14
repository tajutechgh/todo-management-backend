package com.tajutechgh.todobackend.service;

import com.tajutechgh.todobackend.dto.UserDto;

public interface UserService {
    
    UserDto getCurrentUserProfile(String username);
}
