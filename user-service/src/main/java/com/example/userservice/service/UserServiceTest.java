package com.example.userservice.service;

import com.example.userservice.dto.UserRequestDTO;
import com.example.userservice.entity.User;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.talonone.TalonOneClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private TalonOneClient talonOneClient;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_success() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setName("John Doe");
        dto.setEmail("john@example.com");
        dto.setPhone("+1234567890");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(i -> {
            User u = i.getArgument(0);
            u.setId(1L);
            return u;
        });
        doNothing().when(talonOneClient).registerUserInTalonOne(anyString(), anyString(), anyString(), anyString());

        var response = userService.registerUser(dto);
        assertEquals("John Doe", response.getName());
        assertEquals("john@example.com", response.getEmail());
        assertEquals("+1234567890", response.getPhone());
        verify(talonOneClient, times(1)).registerUserInTalonOne(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void getUserById_notFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void updateUser_success() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setName("Jane Doe");
        dto.setEmail("jane@example.com");
        dto.setPhone("+1987654321");
        User user = User.builder().id(1L).name("Old").email("old@example.com").phone("+1111111111").totalOrders(0).totalSpent(0.0).build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));
        var response = userService.updateUser(1L, dto);
        assertEquals("Jane Doe", response.getName());
        assertEquals("jane@example.com", response.getEmail());
        assertEquals("+1987654321", response.getPhone());
    }

    @Test
    void deleteUser_notFound() {
        when(userRepository.existsById(1L)).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));
    }
}
