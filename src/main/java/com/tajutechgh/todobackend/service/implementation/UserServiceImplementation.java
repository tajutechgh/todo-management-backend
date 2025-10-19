package com.tajutechgh.todobackend.service.implementation;

import com.tajutechgh.todobackend.dto.UserDto;
import com.tajutechgh.todobackend.entity.Role;
import com.tajutechgh.todobackend.entity.User;
import com.tajutechgh.todobackend.exception.ResourceNotFoundException;
import com.tajutechgh.todobackend.exception.TodoAPIException;
import com.tajutechgh.todobackend.mapper.UserMapper;
import com.tajutechgh.todobackend.repository.RoleRepository;
import com.tajutechgh.todobackend.repository.UserRepository;
import com.tajutechgh.todobackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImplementation implements UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserServiceImplementation(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

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

    @Override
    public String createUser(UserDto userDto) {

       if (userRepository.existsByEmail(userDto.getEmail())) {
          throw new TodoAPIException(HttpStatus.BAD_REQUEST, "User already exist with this email: " + userDto.getEmail());
       }

       if (userRepository.existsByUsername(userDto.getUsername())) {
          throw new TodoAPIException(HttpStatus.BAD_REQUEST, "User already exist with this username: " + userDto.getUsername());
       }

        User newUser = new User();

        newUser.setName(userDto.getName());
        newUser.setUsername(userDto.getUsername());
        newUser.setEmail(userDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));

        String newUserRole = userDto.getRole();

        System.out.println("new user role is: " +newUserRole);

        Set<Role> roles = new HashSet<>();

       if (newUserRole.equals("USER")) {

          Role userRole = roleRepository.findByName("USER");

          roles.add(userRole);

          newUser.setRoles(roles);

          User saveNewUser = userRepository.save(newUser);

          UserMapper.mapToUserDto(saveNewUser);

       } else if (newUserRole.equals("ADMIN")) {

          Role userRole = roleRepository.findByName("ADMIN");

          roles.add(userRole);

          newUser.setRoles(roles);

          User saveNewUser = userRepository.save(newUser);

          UserMapper.mapToUserDto(saveNewUser);
       }

       return "New user created successfully!";
    }

   @Override
   public UserDto getUserById(Integer userId) {

       User user = userRepository.findById(userId).orElseThrow(

               () -> new ResourceNotFoundException( "User", "id", userId)
       );

       return UserMapper.mapToUserDto(user);
   }

   @Override
   public List<UserDto> getAllUser() {

       List<User> users = userRepository.findAll();

       return users.stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
   }
}
