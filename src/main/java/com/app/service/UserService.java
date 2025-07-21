package com.app.service;

import com.app.model.User;
import com.app.model.UserResponse;
import com.app.model.UpdateUserStatsRequest;
import com.app.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service for user-related business logic.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Fetches user details by ID.
     * @param id The ID of the user.
     * @return UserResponse containing user details.
     */
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        return UserResponse.fromEntity(user);
    }

    /**
     * Updates user's totalOrders and totalSpent statistics.
     * @param id The ID of the user.
     * @param request The request containing updated stats.
     * @return Updated UserResponse.
     */
    public UserResponse updateUserStats(Long id, UpdateUserStatsRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        user.setTotalOrders(request.getTotalOrders());
        user.setTotalSpent(request.getTotalSpent());
        userRepository.save(user);
        return UserResponse.fromEntity(user);
    }

    /**
     * Updates user statistics after an order is placed.
     * @param userId The user ID.
     * @param orderTotal The total amount of the new order.
     */
    public void incrementUserStats(Long userId, double orderTotal) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        user.setTotalOrders(user.getTotalOrders() + 1);
        user.setTotalSpent(user.getTotalSpent() + orderTotal);
        userRepository.save(user);
    }
}
