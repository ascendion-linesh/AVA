package com.example.orderservice.service;

import com.example.orderservice.dto.*;
import com.example.orderservice.entity.Order;
import com.example.orderservice.exception.OrderException;
import com.example.orderservice.feign.RewardsServiceClient;
import com.example.orderservice.feign.UserServiceClient;
import com.example.orderservice.kafka.OrderEvent;
import com.example.orderservice.kafka.OrderEventProducer;
import com.example.orderservice.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final UserServiceClient userServiceClient;
    private final RewardsServiceClient rewardsServiceClient;
    private final OrderEventProducer orderEventProducer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) {
        // Validate user
        UserDto user = userServiceClient.getUserById(orderRequestDto.getUserId());
        if (user == null) {
            throw new OrderException("User not found");
        }

        // Calculate total amount
        BigDecimal totalAmount = orderRequestDto.getCartItems().stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculate discount via rewards-service
        Map<String, Object> discountPayload = new HashMap<>();
        discountPayload.put("userId", orderRequestDto.getUserId());
        discountPayload.put("totalAmount", totalAmount);
        discountPayload.put("cartItems", orderRequestDto.getCartItems());
        Map<String, BigDecimal> discountResponse = rewardsServiceClient.calculateDiscount(discountPayload);
        BigDecimal discount = discountResponse.getOrDefault("discount", BigDecimal.ZERO);

        // Persist order
        String cartItemsJson;
        try {
            cartItemsJson = objectMapper.writeValueAsString(orderRequestDto.getCartItems());
        } catch (JsonProcessingException e) {
            throw new OrderException("Failed to serialize cart items");
        }
        Order order = Order.builder()
                .userId(orderRequestDto.getUserId())
                .cartItems(cartItemsJson)
                .totalAmount(totalAmount)
                .discount(discount)
                .build();
        order = orderRepository.save(order);

        // Publish order event
        OrderEvent orderEvent = OrderEvent.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .cartItems((List<Object>) (Object) orderRequestDto.getCartItems())
                .totalAmount(order.getTotalAmount())
                .discount(order.getDiscount())
                .createdAt(order.getCreatedAt())
                .build();
        orderEventProducer.publishOrderEvent(orderEvent);

        // Prepare response
        return OrderResponseDto.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .cartItems(orderRequestDto.getCartItems())
                .totalAmount(order.getTotalAmount())
                .discount(order.getDiscount())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
