package com.flightbooking.flightBookingSystem.controller;

import com.flightbooking.flightBookingSystem.dto.UserDTO;
import com.flightbooking.flightBookingSystem.entity.User;
import com.flightbooking.flightBookingSystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Create User
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(userDTO));
    }

    // Get user by email
    @GetMapping
    public ResponseEntity<?> getUserByEmail(@RequestParam String emailId) {
        return ResponseEntity.ok(userService.getUserByMailId(emailId));
    }

    // get all user
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // delete user
    @DeleteMapping("/{mailId}")
    public ResponseEntity<String> deleteUser(@PathVariable String mailId) {
        userService.deleteUser(mailId);
        return ResponseEntity.ok("User Deleted Successfully");
    }
}
