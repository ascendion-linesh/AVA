package com.app.service;

import com.app.model.CartRequest;
import com.app.model.RewardsResponse;
import com.app.talonone.TalonOneClient;
lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for rewards and discounts business logic.
 */
@Service
@RequiredArgsConstructor
public class RewardsService {

    private final TalonOneClient talonOneClient;

    /**
     * Evaluates rewards for the provided cart details.
     * Updates user profile and evaluates session via Talon.One.
     * @param cartRequest The cart request.
     * @return The rewards response.
     */
    @Transactional(readOnly = true)
    public RewardsResponse evaluateRewards(CartRequest cartRequest) {
        // Update user profile in Talon.One
        talonOneClient.updateProfile(cartRequest.getUserId(), cartRequest);

        // Evaluate session in Talon.One
        return talonOneClient.evaluateSession(cartRequest.getUserId(), cartRequest);
    }

    /**
     * Confirms loyalty point usage for the user via Talon.One.
     * @param userId The user ID.
     * @param total The total amount for which loyalty points are used.
     */
    @Transactional
    public void confirmLoyalty(String userId, double total) {
        talonOneClient.confirmLoyalty(userId, total);
    }
}
