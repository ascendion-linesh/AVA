package com.example.orderservice.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {
    private UUID orderId;
    private UUID userId;
    private List<Object> cartItems;
    private BigDecimal totalAmount;
    private BigDecimal discount;
    private LocalDateTime createdAt;
}
