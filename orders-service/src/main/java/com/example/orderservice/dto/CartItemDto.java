package com.example.orderservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CartItemDto {
    @NotNull
    @Schema(description = "Product ID", example = "c1b2d3e4-f5a6-7b8c-9d0e-1f2a3b4c5d6e")
    private UUID productId;

    @Min(1)
    @Schema(description = "Quantity", example = "2")
    private int quantity;

    @NotNull
    @Schema(description = "Unit price", example = "19.99")
    private BigDecimal unitPrice;
}
