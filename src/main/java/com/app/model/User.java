package com.app.model;

import jakarta.persistence.*;
lombok.Data;
lombok.NoArgsConstructor;

import java.util.List;

/**
 * Entity representing a user in the system.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Integer totalOrders = 0;

    @Column(nullable = false)
    private Double totalSpent = 0.0;

    // One user can have many orders
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;
}
