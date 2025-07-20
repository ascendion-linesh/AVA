package com.example.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO for user creation and update requests.
 */
@Data
public class UserRequestDto {
    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotNull(message = "Total orders is required")
    @Min(value = 0, message = "Total orders must be non-negative")
    private Integer totalOrders;

    @NotNull(message = "Total spent is required")
    @Min(value = 0, message = "Total spent must be non-negative")
    private BigDecimal totalSpent;
}
