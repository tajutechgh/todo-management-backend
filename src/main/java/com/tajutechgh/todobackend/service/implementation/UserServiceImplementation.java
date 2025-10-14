package com.tajutechgh.todobackend.service.implementation;

import com.tajutechgh.todobackend.dto.UserDto;
import com.tajutechgh.todobackend.entity.Role;
import com.tajutechgh.todobackend.entity.User;
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

        User currentUser = new User(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                roleString
        );
        
        return UserMapper.mapToUserDto(currentUser);
    }
}
