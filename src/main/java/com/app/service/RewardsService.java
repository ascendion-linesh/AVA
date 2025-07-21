package com.app.service;

import com.app.model.CartRequest;
import com.app.model.OrderRequest;
import com.app.model.RewardsResponse;
import com.app.talonone.TalonOneClient;
lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service layer for rewards and discounts logic, integrating with Talon.One API.
 */
@Service
@RequiredArgsConstructor
public class RewardsService {

    private final TalonOneClient talonOneClient;

    /**
     * Evaluates rewards and discounts for a given cart by interacting with Talon.One.
     * @param cartRequest The cart request payload.
     * @return RewardsResponse containing applicable rewards/discounts.
     */
    @Transactional(readOnly = true)
    public RewardsResponse evaluateCart(CartRequest cartRequest) {
        // Update user profile in Talon.One
        talonOneClient.updateProfile(cartRequest.getUserId(), cartRequest);

        // Evaluate session in Talon.One
        RewardsResponse response = talonOneClient.evaluateSession(cartRequest.getUserId(), cartRequest);

        return response;
    }

    /**
     * Evaluates rewards for an order (wrapper for evaluateCart).
     * @param orderRequest The order request payload.
     * @return RewardsResponse containing applicable rewards/discounts.
     */
    @Transactional(readOnly = true)
    public RewardsResponse evaluateOrder(OrderRequest orderRequest) {
        CartRequest cartRequest = new CartRequest(orderRequest.getUserId(), orderRequest.getItems(), orderRequest.getTotal());
        return evaluateCart(cartRequest);
    }

    /**
     * Confirms loyalty point usage for a user/order in Talon.One.
     * @param userId The user ID.
     * @param total The total amount for the order.
     */
    @Transactional
    public void confirmLoyalty(String userId, double total) {
        talonOneClient.confirmLoyalty(userId, total);
    }
}
