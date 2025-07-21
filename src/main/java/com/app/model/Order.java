package com.app.model;

import jakarta.persistence.*;
lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity representing an order placed by a user.
 */
@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal subtotal;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal discount = BigDecimal.ZERO;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal total;

    @Column(nullable = false)
    private Integer loyaltyPointsApplied = 0;

    @Column(nullable = false, length = 32)
    private String status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Utility method for mapping to DTO (optional)
    public OrderResponse toDto() {
        return OrderResponse.builder()
                .orderId(this.id)
                .userId(this.user.getId())
                .items(this.items != null ? this.items.stream().map(Item::toDto).toList() : null)
                .subtotal(this.subtotal)
                .discount(this.discount)
                .total(this.total)
                .loyaltyPointsApplied(this.loyaltyPointsApplied)
                .status(this.status)
                .createdAt(this.createdAt)
                .build();
    }
}
