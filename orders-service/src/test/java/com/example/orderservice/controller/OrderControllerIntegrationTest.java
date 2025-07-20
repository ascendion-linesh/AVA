package com.example.orderservice.controller;

import com.example.orderservice.dto.CartItemDto;
import com.example.orderservice.dto.OrderRequestDto;
import com.example.orderservice.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testPlaceOrder_ValidRequest_ReturnsCreated() throws Exception {
        OrderRequestDto request = new OrderRequestDto();
        request.setUserId(UUID.randomUUID());
        CartItemDto item = new CartItemDto();
        item.setProductId(UUID.randomUUID());
        item.setQuantity(1);
        item.setUnitPrice(new BigDecimal("15.00"));
        request.setCartItems(List.of(item));

        Mockito.when(orderService.placeOrder(any())).thenReturn(null); // We only test status

        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}
