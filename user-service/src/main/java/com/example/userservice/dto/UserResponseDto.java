package com.example.userservice.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO for user responses.
 */
@Data
@Builder
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Integer totalOrders;
    private BigDecimal totalSpent;
}
