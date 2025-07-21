package com.app.service;

import com.app.model.*;
import com.app.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for order-related business logic.
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserService userService;
    private final RewardsService rewardsService;
    private final OrderRepository orderRepository;

    /**
     * Places a new order, applies rewards, saves the order, and updates user stats.
     * @param request The order placement request.
     * @return The response containing order details.
     */
    @Transactional
    public OrderResponse placeOrder(PlaceOrderRequest request) {
        // Fetch user
        UserResponse userResponse = userService.getUserById(request.getUserId());
        if (userResponse == null) {
            throw new EntityNotFoundException("User not found with id: " + request.getUserId());
        }

        // Evaluate rewards/discounts
        CartRequest cartRequest = new CartRequest(request.getUserId(), request.getItems());
        RewardsResponse rewards = rewardsService.evaluateRewards(cartRequest);

        // Calculate total with discount
        double originalTotal = request.getItems().stream().mapToDouble(Item::getPrice).sum();
        double discount = rewards.getDiscountAmount();
        double finalTotal = Math.max(0, originalTotal - discount);

        // Create and save order
        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setItems(request.getItems());
        order.setOriginalTotal(originalTotal);
        order.setDiscount(discount);
        order.setFinalTotal(finalTotal);
        order.setStatus("PLACED");
        orderRepository.save(order);

        // Update user statistics
        userService.incrementUserStats(request.getUserId(), finalTotal);

        // Confirm loyalty point usage
        rewardsService.confirmLoyalty(request.getUserId(), finalTotal);

        return OrderResponse.fromEntity(order, rewards);
    }
}
