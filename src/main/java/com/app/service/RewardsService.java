package com.app.service;

import com.app.client.TalonOneClient;
import com.app.model.RewardsEvaluationRequest;
import com.app.model.RewardsResponse;
lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service layer for integrating with Talon.One to evaluate rewards and confirm loyalty points.
 */
@Service
@RequiredArgsConstructor
public class RewardsService {

    private final TalonOneClient talonOneClient;

    /**
     * Evaluates rewards and discounts for a given cart/session.
     * Updates the user profile in Talon.One, evaluates the session, and returns the rewards.
     * @param request The reward evaluation request containing user and session data.
     * @return RewardsResponse containing personalized rewards and discounts.
     */
    public RewardsResponse evaluateRewards(RewardsEvaluationRequest request) {
        // Step 1: Update user profile in Talon.One
        talonOneClient.updateProfile(request.getUserId(), request.getUserProfileAttributes());

        // Step 2: Evaluate the session for rewards
        RewardsResponse rewardsResponse = talonOneClient.evaluateSession(request);

        // Step 3: Return the rewards response
        return rewardsResponse;
    }

    /**
     * Confirms the usage of loyalty points for a user after a successful transaction.
     * @param userId The user's unique identifier.
     * @param total The total amount for which loyalty points are being confirmed.
     */
    public void confirmLoyalty(String userId, double total) {
        talonOneClient.confirmLoyalty(userId, total);
    }
}
