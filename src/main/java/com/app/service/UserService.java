package com.app.service;

import com.app.model.UserUpdateRequest;
import com.app.model.UserResponse;
import com.app.model.OrderResponse;
import com.app.entity.User;
import com.app.repository.UserRepository;
lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service layer for user management and statistics updates.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Fetch user details by ID.
     * @param id The user ID.
     * @return UserResponse DTO if found, otherwise null.
     */
    public UserResponse getUserById(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        return userOpt.map(this::mapToUserResponse).orElse(null);
    }

    /**
     * Update user's totalOrders and totalSpent.
     * @param id The user ID.
     * @param request The update request DTO.
     * @return Updated UserResponse DTO if user exists, otherwise null.
     */
    public UserResponse updateUserStats(Long id, UserUpdateRequest request) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return null;
        }
        User user = userOpt.get();
        user.setTotalOrders(request.getTotalOrders());
        user.setTotalSpent(request.getTotalSpent());
        userRepository.save(user);
        return mapToUserResponse(user);
    }

    /**
     * Update user statistics after an order is placed.
     * @param userId The user ID.
     * @param order The order response DTO.
     */
    public void updateUserStatsAfterOrder(Long userId, OrderResponse order) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return;
        }
        User user = userOpt.get();
        user.setTotalOrders(user.getTotalOrders() + 1);
        user.setTotalSpent(user.getTotalSpent().add(order.getTotalAmount()));
        userRepository.save(user);
    }

    // Maps User entity to UserResponse DTO.
    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .totalOrders(user.getTotalOrders())
                .totalSpent(user.getTotalSpent())
                // Add other fields as needed
                .build();
    }
}
