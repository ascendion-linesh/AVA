package com.app.service;

import com.app.model.User;
import com.app.model.ProfileDTO;
import com.app.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service layer for user-related business logic.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Fetches a user by their ID.
     * @param id The user ID.
     * @return The User entity.
     * @throws EntityNotFoundException if user not found.
     */
    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    /**
     * Fetches a user profile DTO by user ID.
     * @param id The user ID.
     * @return ProfileDTO or null if not found.
     */
    @Transactional(readOnly = true)
    public ProfileDTO getUserProfileById(Long id) {
        return userRepository.findById(id)
                .map(user -> new ProfileDTO(user.getId(), user.getEmail(), user.getTotalOrders(), user.getTotalSpent()))
                .orElse(null);
    }

    /**
     * Updates the user's statistics (totalOrders, totalSpent) based on ProfileDTO.
     * @param id User ID.
     * @param profileDTO DTO with updated stats.
     * @return true if updated, false if user not found.
     */
    @Transactional
    public boolean updateUserStats(Long id, ProfileDTO profileDTO) {
        return userRepository.findById(id).map(user -> {
            user.setTotalOrders(profileDTO.getTotalOrders());
            user.setTotalSpent(profileDTO.getTotalSpent());
            userRepository.save(user);
            return true;
        }).orElse(false);
    }

    /**
     * Updates user statistics after a new order is placed.
     * @param order The order just placed.
     */
    @Transactional
    public void updateStatsAfterOrder(com.app.model.Order order) {
        User user = order.getUser();
        user.setTotalOrders(user.getTotalOrders() + 1);
        user.setTotalSpent(user.getTotalSpent() + order.getTotal());
        userRepository.save(user);
    }

    /**
     * Saves the user entity.
     * @param user The user to save.
     * @return The saved user.
     */
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }
}
