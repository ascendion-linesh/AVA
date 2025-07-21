package com.app.service;

import com.app.model.OrderCreateRequest;
import com.app.model.OrderResponse;
import com.app.model.RewardsResponse;
import com.app.entity.Order;
import com.app.entity.User;
import com.app.repository.OrderRepository;
lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.stream.Collectors;

/**
 * Service layer for order processing, reward application, and persistence.
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserService userService;
    private final RewardsService rewardsService;
    private final OrderRepository orderRepository;

    /**
     * Places a new order, applies rewards/discounts, saves the order, and returns the response.
     * @param request The order creation request DTO.
     * @param rewards The evaluated rewards/discounts for the order.
     * @return OrderResponse DTO representing the saved order.
     */
    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request, RewardsResponse rewards) {
        // 1. Retrieve the user
        User user = userService.getUserById(request.getUserId());

        // 2. Calculate subtotal and apply discounts/loyalty points from rewards
        BigDecimal subtotal = request.getItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal discount = rewards != null && rewards.getDiscountAmount() != null
                ? rewards.getDiscountAmount()
                : BigDecimal.ZERO;

        BigDecimal total = subtotal.subtract(discount).max(BigDecimal.ZERO);

        // Optionally, handle loyalty points or other reward effects here
        Integer loyaltyPointsApplied = rewards != null ? rewards.getLoyaltyPointsApplied() : 0;

        // 3. Create and save the Order entity
        Order order = new Order();
        order.setUser(user);
        order.setItems(
                request.getItems().stream()
                        .map(itemDto -> itemDto.toEntity()) // Assume toEntity() exists on DTO
                        .collect(Collectors.toList())
        );
        order.setSubtotal(subtotal);
        order.setDiscount(discount);
        order.setTotal(total);
        order.setLoyaltyPointsApplied(loyaltyPointsApplied);
        order.setStatus("CREATED"); // or whatever default status

        Order savedOrder = orderRepository.save(order);

        // 4. Return an OrderResponse DTO
        return OrderResponse.builder()
                .orderId(savedOrder.getId())
                .userId(user.getId())
                .items(
                        savedOrder.getItems().stream()
                                .map(item -> item.toDto()) // Assume toDto() exists on entity
                                .collect(Collectors.toList())
                )
                .subtotal(savedOrder.getSubtotal())
                .discount(savedOrder.getDiscount())
                .total(savedOrder.getTotal())
                .loyaltyPointsApplied(savedOrder.getLoyaltyPointsApplied())
                .status(savedOrder.getStatus())
                .createdAt(savedOrder.getCreatedAt())
                .build();
    }
}
