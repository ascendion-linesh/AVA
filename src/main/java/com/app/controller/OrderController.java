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
     * Place a new order, evaluate rewards, save order, and update user stats.
     */
    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody @Valid OrderRequest orderRequest) {
        // Evaluate rewards for the order
        RewardsResponse rewards = rewardsService.evaluateRewards(orderRequest.getCart());
        // Save the order (implementation assumes orderService handles rewards application)
        Order savedOrder = orderService.placeOrder(orderRequest, rewards);
        // Update user stats (implementation assumes orderService or userService handles this)
        userService.updateStatsAfterOrder(orderRequest.getUserId(), savedOrder);
        return ResponseEntity.status(201).body(savedOrder);
    }
}
