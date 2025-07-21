package com.app.repository;

import com.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for User entity.
 * <p>
 * Provides CRUD operations and query methods for User data using Spring Data JPA.
 * This interface should only contain default methods inherited from JpaRepository.
 * No custom query methods or business logic should be added here.
 * </p>
 */
public interface UserRepository extends JpaRepository<User, Long> {
    // Default query methods provided by JpaRepository are sufficient.
}
