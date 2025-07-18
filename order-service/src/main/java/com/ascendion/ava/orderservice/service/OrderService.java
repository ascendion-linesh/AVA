package com.ascendion.ava.orderservice.service;

import com.ascendion.ava.orderservice.client.RewardsServiceClient;
import com.ascendion.ava.orderservice.client.UserServiceClient;
import com.ascendion.ava.orderservice.dto.*;
import com.ascendion.ava.orderservice.entity.Order;
import com.ascendion.ava.orderservice.event.OrderEvent;
import com.ascendion.ava.orderservice.event.OrderEventProducer;
import com.ascendion.ava.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final UserServiceClient userServiceClient;
    private final RewardsServiceClient rewardsServiceClient;
    private final OrderEventProducer orderEventProducer;

    @Transactional
    public OrderResponse placeOrder(OrderRequest orderRequest) {
        // Validate user
        UserDto user = userServiceClient.getUserById(orderRequest.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        // Calculate discount
        DiscountRequestDto discountRequest = new DiscountRequestDto();
        discountRequest.setUserId(orderRequest.getUserId());
        discountRequest.setCartItems(orderRequest.getCartItems());
        discountRequest.setTotalAmount(orderRequest.getTotalAmount());
        DiscountResponseDto discountResponse = rewardsServiceClient.calculateDiscount(discountRequest);
        BigDecimal discount = discountResponse != null && discountResponse.getDiscount() != null ? discountResponse.getDiscount() : BigDecimal.ZERO;

        // Persist order
        UUID orderId = UUID.randomUUID();
        String cartItemsJson = JsonUtils.toJson(orderRequest.getCartItems());
        Order order = Order.builder()
                .id(orderId)
                .userId(orderRequest.getUserId())
                .cartItems(cartItemsJson)
                .totalAmount(orderRequest.getTotalAmount())
                .discount(discount)
                .build();
        order = orderRepository.save(order);

        // Publish event
        OrderEvent orderEvent = OrderEvent.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .cartItems(orderRequest.getCartItems())
                .totalAmount(order.getTotalAmount())
                .discount(order.getDiscount())
                .createdAt(order.getCreatedAt())
                .build();
        orderEventProducer.publishOrderEvent(orderEvent);

        // Prepare response
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .cartItems(orderRequest.getCartItems())
                .totalAmount(order.getTotalAmount())
                .discount(order.getDiscount())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
