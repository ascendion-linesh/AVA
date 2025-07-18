package com.ascendion.ava.orderservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {
    private UUID id;
    private UUID userId;
    private List<?> cartItems;
    private BigDecimal totalAmount;
    private BigDecimal discount;
    private OffsetDateTime createdAt;
}
