package com.tajutechgh.todobackend.mapper;

import com.tajutechgh.todobackend.dto.UserDto;
import com.tajutechgh.todobackend.entity.Role;
import com.tajutechgh.todobackend.entity.User;

import java.util.stream.Collectors;

public class UserMapper {
    
    public static UserDto mapToUserDto(User user){
        
        UserDto userDto = new UserDto();
        
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        
        return userDto;
    }
    
    public static  User mapToUser(UserDto userDto){
        
        User user = new User();
        
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());;
        user.setRoles(user.getRoles());

        return user;
    }
}
