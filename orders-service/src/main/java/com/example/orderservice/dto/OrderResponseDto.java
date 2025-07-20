package com.example.orderservice.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class OrderResponseDto {
    private UUID orderId;
    private UUID userId;
    private List<CartItemDto> cartItems;
    private BigDecimal totalAmount;
    private BigDecimal discount;
    private LocalDateTime createdAt;
}
