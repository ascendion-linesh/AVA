package com.ascendion.ava.orderservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class DiscountRequestDto {
    private UUID userId;
    private List<CartItemDto> cartItems;
    private BigDecimal totalAmount;
}
