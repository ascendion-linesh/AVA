package com.example.userservice.service;

import com.example.userservice.dto.UserRequestDTO;
import com.example.userservice.dto.UserResponseDTO;

import java.util.List;

public interface UserService {
    UserResponseDTO registerUser(UserRequestDTO userRequestDTO);
    UserResponseDTO getUserById(Long id);
    UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO);
    void deleteUser(Long id);
    UserResponseDTO getUserByEmail(String email);
    List<UserResponseDTO> getAllUsers();
}
