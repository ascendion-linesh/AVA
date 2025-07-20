package com.example.userservice.service;

import com.example.userservice.dto.UserRequestDto;
import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.talonone.TalonOneClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
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
        UserRequestDto dto = new UserRequestDto();
        dto.setName("Test User");
        dto.setEmail("test@example.com");
        dto.setPhone("1234567890");
        dto.setTotalOrders(1);
        dto.setTotalSpent(BigDecimal.valueOf(50));

        User savedUser = User.builder()
                .id(1L)
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .totalOrders(dto.getTotalOrders())
                .totalSpent(dto.getTotalSpent())
                .build();

        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(talonOneClient.registerUserInTalonOne(anyLong(), anyString(), anyString())).thenReturn(true);

        var response = userService.registerUser(dto);
        assertEquals("Test User", response.getName());
        assertEquals("test@example.com", response.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
        verify(talonOneClient, times(1)).registerUserInTalonOne(anyLong(), anyString(), anyString());
    }

    @Test
    void getUserById_notFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.getUserById(1L));
    }
}
