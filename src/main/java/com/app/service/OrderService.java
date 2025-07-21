package com.app.service;

import com.app.model.Order;
import com.app.model.OrderRequest;
import com.app.model.CartRequest;
import com.app.model.RewardsResponse;
import com.app.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service layer for order-related business logic.
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserService userService;
    private final RewardsService rewardsService;
    private final OrderRepository orderRepository;

    /**
     * Places a new order, evaluates rewards, saves the order, and updates user stats.
     * @param orderRequest The order request payload.
     * @return The saved Order entity.
     */
    @Transactional
    public Order saveOrder(OrderRequest orderRequest, RewardsResponse rewards) {
        // Retrieve user
        var user = userService.getUser(orderRequest.getUserId());

        // Calculate final total after applying rewards/discounts
        double discount = rewards != null ? rewards.getDiscountAmount() : 0.0;
        double finalTotal = orderRequest.getTotal() - discount;

        // Create Order entity
        Order order = new Order();
        order.setUser(user);
        order.setItems(orderRequest.getItems());
        order.setTotal(finalTotal);
        order.setDiscount(discount);
        order.setCreatedAt(java.time.Instant.now());

        // Save order
        Order savedOrder = orderRepository.save(order);

        // Update user statistics
        user.setTotalOrders(user.getTotalOrders() + 1);
        user.setTotalSpent(user.getTotalSpent() + finalTotal);
        userService.save(user);

        // Confirm loyalty point usage if applicable
        if (rewards != null && rewards.isLoyaltyUsed()) {
            rewardsService.confirmLoyalty(user.getId().toString(), finalTotal);
        }

        return savedOrder;
    }

    /**
     * Places an order: retrieves user, evaluates rewards, saves order, updates stats, and confirms loyalty.
     * @param orderRequest The order request payload.
     * @return The saved Order entity.
     */
    @Transactional
    public Order placeOrder(OrderRequest orderRequest) {
        // Retrieve user
        var user = userService.getUser(orderRequest.getUserId());

        // Evaluate rewards for the cart
        CartRequest cartRequest = new CartRequest(orderRequest.getUserId(), orderRequest.getItems(), orderRequest.getTotal());
        RewardsResponse rewards = rewardsService.evaluateCart(cartRequest);

        // Save order with applied rewards
        Order order = saveOrder(orderRequest, rewards);

        // Confirm loyalty point usage if applicable
        if (rewards != null && rewards.isLoyaltyUsed()) {
            rewardsService.confirmLoyalty(user.getId().toString(), order.getTotal());
        }

        return order;
    }
}
