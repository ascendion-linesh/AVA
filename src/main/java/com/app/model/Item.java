package com.app.model;

import jakarta.persistence.*;
lombok.*;

import java.math.BigDecimal;

/**
 * Entity representing an item in an order.
 */
@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(nullable = false, length = 128)
    private String productId;

    @Column(nullable = false, length = 256)
    private String productName;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    // Utility method for mapping to DTO (optional)
    public ItemDTO toDto() {
        return ItemDTO.builder()
                .productId(this.productId)
                .productName(this.productName)
                .quantity(this.quantity)
                .price(this.price)
                .build();
    }
}
