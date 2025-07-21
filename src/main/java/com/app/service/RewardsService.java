package com.app.service;

import com.app.model.*;
import com.app.talonone.TalonOneClient;
lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service for handling rewards and discount logic, integrating with Talon.One.
 */
@Service
@RequiredArgsConstructor
public class RewardsService {

    private final TalonOneClient talonOneClient;

    /**
     * Evaluates rewards and discounts for a given cart by updating the user profile
     * and evaluating the session via Talon.One.
     * @param cartRequest The cart request containing user and item details.
     * @return RewardsResponse containing applicable discounts and rewards.
     */
    public RewardsResponse evaluateRewards(CartRequest cartRequest) {
        // Update user profile in Talon.One
        ProfileDTO profileDTO = new ProfileDTO(cartRequest.getUserId());
        talonOneClient.updateProfile(profileDTO);

        // Evaluate session for discounts/rewards
        SessionDTO sessionDTO = new SessionDTO(cartRequest.getUserId(), cartRequest.getItems());
        return talonOneClient.evaluateSession(sessionDTO);
    }

    /**
     * Confirms loyalty point usage for a user after an order is placed.
     * @param userId The ID of the user.
     * @param total The total amount of the order.
     */
    public void confirmLoyalty(Long userId, double total) {
        talonOneClient.confirmLoyalty(userId, total);
    }

    /**
     * (Optional) For side-effect reward evaluation before order placement.
     * @param request The place order request.
     */
    public void evaluateRewardsForOrder(PlaceOrderRequest request) {
        CartRequest cartRequest = new CartRequest(request.getUserId(), request.getItems());
        evaluateRewards(cartRequest);
    }
}
