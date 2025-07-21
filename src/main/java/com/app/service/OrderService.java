package com.app.service;

import com.app.exception.ResourceNotFoundException;
import com.app.model.*;
import com.app.repository.OrderRepository;
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
     * Places a new order: evaluates discounts, saves order, updates user stats, and confirms loyalty usage.
     * @param orderRequest The order request.
     * @param rewardsResponse The evaluated rewards for the order.
     * @return The saved order.
     */
    @Transactional
    public Order placeOrder(OrderRequest orderRequest, RewardsResponse rewardsResponse) {
        // Retrieve user
        User user = userService.getUser(orderRequest.getUserId());

        // Calculate discount and final amount
        double discount = rewardsResponse != null ? rewardsResponse.getDiscountAmount() : 0.0;
        double originalTotal = orderRequest.getCart().getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        double finalTotal = Math.max(0.0, originalTotal - discount);

        // Create and save order
        Order order = new Order();
        order.setUser(user);
        order.setItems(orderRequest.getCart().getItems());
        order.setDiscount(discount);
        order.setTotalAmount(finalTotal);
        order.setStatus("PLACED");
        orderRepository.save(order);

        // Update user statistics
        userService.updateStatsAfterOrder(user.getId(), order);

        // Confirm loyalty point usage
        rewardsService.confirmLoyalty(user.getId().toString(), finalTotal);

        return order;
    }
}
