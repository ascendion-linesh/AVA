package com.example.orderservice.kafka;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventProducer {
    private static final Logger logger = LoggerFactory.getLogger(OrderEventProducer.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.orders}")
    private String ordersTopic;

    public void publishOrderEvent(OrderEvent orderEvent) {
        logger.info("Publishing order event to Kafka: {}", orderEvent);
        kafkaTemplate.send(ordersTopic, orderEvent.getOrderId().toString(), orderEvent);
    }
}
