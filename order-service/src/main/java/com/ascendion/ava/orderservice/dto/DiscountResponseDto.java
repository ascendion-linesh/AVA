package com.ascendion.ava.orderservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DiscountResponseDto {
    private BigDecimal discount;
}
