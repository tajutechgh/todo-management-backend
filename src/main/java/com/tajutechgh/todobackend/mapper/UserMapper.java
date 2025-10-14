package com.tajutechgh.todobackend.mapper;

import com.tajutechgh.todobackend.dto.UserDto;
import com.tajutechgh.todobackend.entity.User;

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
        
        return  new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getUsername(),
                userDto.getEmail(),
                userDto.getRole()
        );
        
    }
}
