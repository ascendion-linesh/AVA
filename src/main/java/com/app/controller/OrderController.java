package com.app.controller;

import com.app.model.OrderCreateRequest;
import com.app.model.OrderResponse;
import com.app.model.RewardsEvaluationRequest;
import com.app.model.RewardsResponse;
import com.app.service.OrderService;
import com.app.service.RewardsService;
import com.app.service.UserService;
import jakarta.validation.Valid;
lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<OrderResponse> createOrder(
            @RequestBody @Valid OrderCreateRequest request) {

        // Evaluate rewards for the order
        RewardsEvaluationRequest rewardsRequest = new RewardsEvaluationRequest();
        rewardsRequest.setUserId(request.getUserId());
        rewardsRequest.setItems(request.getItems());
        rewardsRequest.setTotalAmount(request.getTotalAmount());
        RewardsResponse rewards = rewardsService.evaluateRewards(rewardsRequest);

        // Save the order (including applied rewards/discounts)
        OrderResponse order = orderService.createOrder(request, rewards);

        // Update user statistics (e.g., totalOrders, totalSpent)
        userService.updateUserStatsAfterOrder(request.getUserId(), order);

        return ResponseEntity.status(201).body(order);
    }
}
