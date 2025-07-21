package com.app.controller;

import com.app.model.OrderResponse;
import com.app.model.PlaceOrderRequest;
import com.app.service.OrderService;
import com.app.service.RewardsService;
import com.app.service.UserService;
import jakarta.validation.Valid;
lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
     * Place a new order, evaluate rewards, save the order, and update user stats.
     */
    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@Valid @RequestBody PlaceOrderRequest request) {
        // Evaluate rewards (side effect, if needed)
        rewardsService.evaluateRewardsForOrder(request);

        // Save the order and update user stats
        OrderResponse orderResponse = orderService.placeOrder(request);

        // Optionally, update user stats if not handled in service
        // userService.updateUserStats(orderResponse.getUserId(), ...);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }
}
