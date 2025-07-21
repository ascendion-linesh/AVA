package com.example.rewards.service;

import com.example.rewards.model.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaOrderListener {
    private static final Logger logger = LoggerFactory.getLogger(KafkaOrderListener.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "order-events", groupId = "rewards-service-group")
    public void listenOrderEvents(String message) {
        try {
            OrderEvent event = objectMapper.readValue(message, OrderEvent.class);
            logger.info("Received order event: {}", event);
            // Confirm loyalty actions, update rewards, etc.
            // (Business logic placeholder)
            logger.info("Processed loyalty actions for orderId: {} and customerId: {}", event.getOrderId(), event.getCustomerId());
        } catch (Exception e) {
            logger.error("Failed to process order event: {}", message, e);
        }
    }
}
