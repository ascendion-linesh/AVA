package com.app.model;

import jakarta.persistence.*;
lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Entity representing a user in the e-commerce application.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 128)
    private String email;

    @Column(nullable = false, length = 64)
    private String name;

    @Column(nullable = false)
    private Integer totalOrders = 0;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal totalSpent = BigDecimal.ZERO;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;
}
