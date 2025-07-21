package com.app.repository;

import com.app.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Order entity.
 * <p>
 * Provides CRUD operations and query methods for Order data using Spring Data JPA.
 * This interface should only contain default methods inherited from JpaRepository.
 * No custom query methods or business logic should be added here.
 * </p>
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Default query methods provided by JpaRepository are sufficient.
}
