package com.example.orderservice.service;

import com.example.orderservice.dto.CartItemDto;
import com.example.orderservice.dto.OrderRequestDto;
import com.example.orderservice.dto.UserDto;
import com.example.orderservice.entity.Order;
import com.example.orderservice.feign.RewardsServiceClient;
import com.example.orderservice.feign.UserServiceClient;
import com.example.orderservice.kafka.OrderEventProducer;
import com.example.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserServiceClient userServiceClient;
    @Mock
    private RewardsServiceClient rewardsServiceClient;
    @Mock
    private OrderEventProducer orderEventProducer;
    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPlaceOrder_Success() {
        UUID userId = UUID.randomUUID();
        CartItemDto item = new CartItemDto();
        item.setProductId(UUID.randomUUID());
        item.setQuantity(2);
        item.setUnitPrice(new BigDecimal("10.00"));
        List<CartItemDto> cartItems = List.of(item);
        OrderRequestDto request = new OrderRequestDto();
        request.setUserId(userId);
        request.setCartItems(cartItems);

        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setName("Test User");
        userDto.setEmail("test@example.com");
        when(userServiceClient.getUserById(userId)).thenReturn(userDto);
        when(rewardsServiceClient.calculateDiscount(any())).thenReturn(Map.of("discount", new BigDecimal("5.00")));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order o = invocation.getArgument(0);
            o.setId(UUID.randomUUID());
            o.setCreatedAt(java.time.LocalDateTime.now());
            return o;
        });

        var response = orderService.placeOrder(request);
        assertNotNull(response.getOrderId());
        assertEquals(userId, response.getUserId());
        assertEquals(new BigDecimal("20.00"), response.getTotalAmount());
        assertEquals(new BigDecimal("5.00"), response.getDiscount());
        verify(orderEventProducer, times(1)).publishOrderEvent(any());
    }
}
