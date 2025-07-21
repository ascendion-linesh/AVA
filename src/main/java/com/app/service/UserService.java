package com.app.service;

import com.app.exception.ResourceNotFoundException;
import com.app.model.ProfileDTO;
import com.app.model.User;
import com.app.model.UserStatsUpdateRequest;
import com.app.repository.UserRepository;
lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * @return The user entity.
     * @throws ResourceNotFoundException if user is not found.
     */
    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    /**
     * Fetches user profile DTO by user ID.
     * @param id The ID of the user.
     * @return The profile DTO.
     */
    @Transactional(readOnly = true)
    public ProfileDTO getUserProfileById(Long id) {
        User user = getUser(id);
        return toProfileDTO(user);
    }

    /**
     * Updates user statistics such as totalOrders and totalSpent.
     * @param id The ID of the user.
     * @param request The request containing updated stats.
     * @return The updated profile DTO.
     */
    @Transactional
    public ProfileDTO updateUserStats(Long id, UserStatsUpdateRequest request) {
        User user = getUser(id);
        user.setTotalOrders(request.getTotalOrders());
        user.setTotalSpent(request.getTotalSpent());
        userRepository.save(user);
        return toProfileDTO(user);
    }

    /**
     * Updates user statistics after a new order is placed.
     * @param userId The ID of the user.
     * @param order The order placed.
     */
    @Transactional
    public void updateStatsAfterOrder(Long userId, com.app.model.Order order) {
        User user = getUser(userId);
        user.setTotalOrders(user.getTotalOrders() + 1);
        user.setTotalSpent(user.getTotalSpent() + order.getTotalAmount());
        userRepository.save(user);
    }

    /**
     * Converts a User entity to ProfileDTO.
     * @param user The user entity.
     * @return The profile DTO.
     */
    private ProfileDTO toProfileDTO(User user) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setTotalOrders(user.getTotalOrders());
        dto.setTotalSpent(user.getTotalSpent());
        // Add other fields as necessary
        return dto;
    }
}
