package com.flightbooking.flightBookingSystem.service;

import com.flightbooking.flightBookingSystem.dto.UserDTO;
import com.flightbooking.flightBookingSystem.entity.User;
import com.flightbooking.flightBookingSystem.exception.custom.DuplicateResourceException;
import com.flightbooking.flightBookingSystem.exception.custom.ResourceNotFoundException;
import com.flightbooking.flightBookingSystem.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // creating user
    public UserDTO createUser(UserDTO userDTO) {
        Optional<User> isExistingUser = userRepository.findByEmail(userDTO.getEmail());
        if(isExistingUser.isPresent()) {
            throw new DuplicateResourceException("User already exist with mail id: " + userDTO.getEmail());
        }

        User user = mapToEntity(userDTO);
        User savedUser = userRepository.save(user);

        return mapToDTO(savedUser);
    }

    // finding user by mailId
    public UserDTO getUserByMailId(String mailId) {
        User user = userRepository.findByEmail(mailId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with mailId: " + mailId));
        return mapToDTO(user);
    }

    // getting all users
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // delete user
    @Transactional
    public void deleteUser(String mail) {
        User user = userRepository
                .findByEmail(mail)
                .orElseThrow(() -> new ResourceNotFoundException("User not Found with email: " + mail));
        userRepository.delete(user);
    }

    private UserDTO mapToDTO(User user) {
        return  new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    private User mapToEntity(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        return user;
    }
}

