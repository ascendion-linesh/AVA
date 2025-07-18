package com.example.rewards.service;

import com.example.rewards.model.OrderEvent;
import com.example.rewards.service.RewardsService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;
import java.util.Set;

@Service
public class KafkaOrderListener {
    private static final Logger logger = LoggerFactory.getLogger(KafkaOrderListener.class);
    private final RewardsService rewardsService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    // For demo idempotency; in production use a persistent store
    private final Set<String> processedOrderIds = new HashSet<>();

    @Autowired
    public KafkaOrderListener(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    @KafkaListener(topics = "order-events", groupId = "rewards-service-group")
    public void listen(ConsumerRecord<String, String> record) {
        try {
            String value = record.value();
            OrderEvent orderEvent = objectMapper.readValue(value, OrderEvent.class);
            if (orderEvent.getOrderId() == null || processedOrderIds.contains(orderEvent.getOrderId())) {
                logger.info("Skipping duplicate or invalid order event: {}", orderEvent.getOrderId());
                return;
            }
            logger.info("Processing order event from Kafka: {}", orderEvent.getOrderId());
            rewardsService.evaluateCart(orderEvent);
            processedOrderIds.add(orderEvent.getOrderId());
            logger.info("Order event processed and loyalty actions confirmed: {}", orderEvent.getOrderId());
        } catch (Exception ex) {
            logger.error("Error processing Kafka order event: {}", record.value(), ex);
        }
    }
}
