package com.tajutechgh.todobackend.service.implementation;

import com.tajutechgh.todobackend.dto.UserDto;
import com.tajutechgh.todobackend.entity.Role;
import com.tajutechgh.todobackend.entity.User;
import com.tajutechgh.todobackend.exception.ResourceNotFoundException;
import com.tajutechgh.todobackend.mapper.TodoMapper;
import com.tajutechgh.todobackend.mapper.UserMapper;
import com.tajutechgh.todobackend.repository.UserRepository;
import com.tajutechgh.todobackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserServiceImplementation implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDto getCurrentUserProfile(String username) {

        User user = userRepository.findByUsername(username).orElseThrow(

                () -> new UsernameNotFoundException("Current user not found")
        );

        String roleString = user.getRoles().stream().map(Role::getName).collect(Collectors.joining(", "));

        UserDto currentUser = new UserDto(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                roleString
        );
        
        return currentUser;
    }

    @Override
    public UserDto updateCurrentUserProfile(Integer id, UserDto userDto) {
        
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );
        
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        
        User updateUser = userRepository.save(user);
        
        return UserMapper.mapToUserDto(updateUser);
    }
}
