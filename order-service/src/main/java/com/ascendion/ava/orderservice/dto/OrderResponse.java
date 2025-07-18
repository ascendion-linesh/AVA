package com.ascendion.ava.orderservice.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class OrderResponse {
    private UUID id;
    private UUID userId;
    private List<CartItemDto> cartItems;
    private BigDecimal totalAmount;
    private BigDecimal discount;
    private OffsetDateTime createdAt;
}
