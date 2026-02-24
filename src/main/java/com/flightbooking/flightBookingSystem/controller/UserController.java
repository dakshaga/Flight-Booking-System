package com.flightbooking.flightBookingSystem.controller;

import com.flightbooking.flightBookingSystem.dto.UserDTO;
import com.flightbooking.flightBookingSystem.payload.ApiResponse;
import com.flightbooking.flightBookingSystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Create User
    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO savedUser = userService.createUser(userDTO);
        ApiResponse<UserDTO> response = new ApiResponse<>(true, "User Created Successfully", savedUser);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    // Get user by email
    @GetMapping
    public ResponseEntity<ApiResponse<UserDTO>> getUserByEmail(@RequestParam String emailId) {
        UserDTO user = userService.getUserByMailId(emailId);
        ApiResponse<UserDTO> response = new ApiResponse<>(true, "User fetched successfully", user);
        return ResponseEntity.ok(response);
    }

    // get all user
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        ApiResponse<List<UserDTO>> response = new ApiResponse<>(true, "Users fetched successfully", users);
        return ResponseEntity.ok(response);
    }

    // delete user
    @DeleteMapping("/{mailId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable String mailId) {
        userService.deleteUser(mailId);
        ApiResponse<Void> response = new ApiResponse<>(true, "User Deleted successfully", null);
        return ResponseEntity.ok(response);
    }
}
