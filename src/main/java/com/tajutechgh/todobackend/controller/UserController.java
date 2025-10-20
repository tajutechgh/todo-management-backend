package com.tajutechgh.todobackend.controller;

import com.tajutechgh.todobackend.dto.UserDto;
import com.tajutechgh.todobackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    
    private final UserService userService;

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

    // TODO: update a current user profile
    @PutMapping("/profile/update/{id}")
    public ResponseEntity<UserDto> updateCurrentUser(@PathVariable(value = "id") Integer id, @RequestBody UserDto userDto) {

        UserDto updateUserDto = userService.updateCurrentUserProfile(id, userDto);

        return new ResponseEntity<>(updateUserDto, HttpStatus.OK);
    }

    // TODO: create new user
    @PostMapping("/create")
    public ResponseEntity<String> createNewUser(@RequestBody UserDto userDto){

       userService.createUser(userDto);

       return new ResponseEntity<>("User created successfully!", HttpStatus.CREATED);
    }

    // TODO: get user by id
    @GetMapping("/get/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(value = "userId") Integer userId){

       UserDto userDto = userService.getUserById(userId);

       return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

   // TODO: get all users
   @GetMapping("/all")
   public ResponseEntity<List<UserDto>> getAllUsers() {

      List<UserDto> userDtos = userService.getAllUser();

      return new ResponseEntity<>(userDtos, HttpStatus.OK);
   }

   // TODO: update a user
   @PutMapping("/update/{userId}")
   public ResponseEntity<String> updateUser(@PathVariable(value = "userId") Integer userId, @RequestBody UserDto userDto) {

      userService.updateUser(userId, userDto);

      return new ResponseEntity<>("User updated successfully!", HttpStatus.OK);
   }

   // TODO: delete a user
   @DeleteMapping("/delete/{userId}")
   public ResponseEntity<String> deleteUser(@PathVariable(value = "userId") Integer userId) {

      userService.deleteUser ( userId );

      return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
   }
}
