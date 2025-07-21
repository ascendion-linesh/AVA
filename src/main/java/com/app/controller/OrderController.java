package com.app.controller;

import com.app.model.Order;
import com.app.model.OrderRequest;
import com.app.model.RewardsResponse;
import com.app.service.OrderService;
import com.app.service.RewardsService;
import com.app.service.UserService;
import jakarta.validation.Valid;
lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for order-related endpoints.
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final RewardsService rewardsService;
    private final UserService userService;

    /**
     * Place an order, evaluate rewards, save order, and update user stats.
     * @param orderRequest Order request payload
     * @return Created order and applied rewards
     */
    @PostMapping
    public ResponseEntity<OrderPlacedResponse> placeOrder(
            @Valid @RequestBody OrderRequest orderRequest
    ) {
        // Evaluate rewards for the order
        RewardsResponse rewards = rewardsService.evaluateOrder(orderRequest);

        // Save the order
        Order order = orderService.saveOrder(orderRequest, rewards);

        // Update user statistics
        userService.updateStatsAfterOrder(order);

        // Return order and rewards info
        return ResponseEntity.status(201).body(new OrderPlacedResponse(order, rewards));
    }

    /**
     * DTO for response containing order and rewards.
     */
    public static class OrderPlacedResponse {
        private final Order order;
        private final RewardsResponse rewards;

        public OrderPlacedResponse(Order order, RewardsResponse rewards) {
            this.order = order;
            this.rewards = rewards;
        }

        public Order getOrder() {
            return order;
        }

        public RewardsResponse getRewards() {
            return rewards;
        }
    }
}
