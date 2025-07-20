package com.example.userservice.service;

import com.example.userservice.dto.UserRequestDto;
import com.example.userservice.dto.UserResponseDto;
import com.example.userservice.entity.User;
import com.example.userservice.exception.ResourceNotFoundException;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.talonone.TalonOneClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service layer for user operations.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TalonOneClient talonOneClient;

    /**
     * Registers a new user and integrates with Talon.One.
     */
    @Transactional
    public UserResponseDto registerUser(UserRequestDto dto) {
        try {
            User user = User.builder()
                    .name(dto.getName())
                    .email(dto.getEmail())
                    .phone(dto.getPhone())
                    .totalOrders(dto.getTotalOrders())
                    .totalSpent(dto.getTotalSpent())
                    .build();
            user = userRepository.save(user);
            boolean talonOneSuccess = talonOneClient.registerUserInTalonOne(user.getId(), user.getEmail(), user.getName());
            if (!talonOneSuccess) {
                log.warn("Talon.One registration failed for user {}", user.getId());
            }
            return mapToResponseDto(user);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalArgumentException("Email or phone already exists");
        }
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return mapToResponseDto(user);
    }

    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return mapToResponseDto(user);
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setTotalOrders(dto.getTotalOrders());
        user.setTotalSpent(dto.getTotalSpent());
        user = userRepository.save(user);
        return mapToResponseDto(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    private UserResponseDto mapToResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .totalOrders(user.getTotalOrders())
                .totalSpent(user.getTotalSpent())
                .build();
    }
}
