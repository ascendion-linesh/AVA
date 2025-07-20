package com.example.orderservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderRequestDto {
    @NotNull
    @Schema(description = "User ID", example = "e7b4b5e2-5f3c-4bb9-8a9e-1d2f3e4c5b6a")
    private UUID userId;

    @NotEmpty
    @Schema(description = "List of cart items")
    private List<CartItemDto> cartItems;
}
