package com.example.userservice.service;

import com.example.userservice.dto.UserRequestDto;
import com.example.userservice.entity.User;
import com.example.userservice.exception.ResourceNotFoundException;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.talonone.TalonOneClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
        UserRequestDto dto = new UserRequestDto();
        dto.setName("Alice");
        dto.setEmail("alice@example.com");
        dto.setPhone("+1234567890");

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(i -> {
            User u = i.getArgument(0);
            u.setId(1L);
            return u;
        });
        doNothing().when(talonOneClient).createCustomerProfile(any(), any(), any(), any());

        var response = userService.registerUser(dto);
        assertNotNull(response);
        assertEquals("Alice", response.getName());
        verify(talonOneClient, times(1)).createCustomerProfile(any(), any(), any(), any());
    }

    @Test
    void getUserById_notFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void getUserByEmail_success() {
        User user = User.builder().id(2L).name("Bob").email("bob@example.com").phone("+1234567891").totalOrders(0).totalSpent(0.0).build();
        when(userRepository.findByEmail("bob@example.com")).thenReturn(Optional.of(user));
        var response = userService.getUserByEmail("bob@example.com");
        assertEquals("Bob", response.getName());
    }
}
