package com.example.rewards.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Represents an order event received from Kafka (order-service integration).
 */
public class OrderEvent {
    private String orderId;
    private String userId;
    private List<OrderItem> items;
    private BigDecimal totalAmount;
    private String status;
    private String eventType;
    private long eventTimestamp;

    // Getters and setters
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public long getEventTimestamp() { return eventTimestamp; }
    public void setEventTimestamp(long eventTimestamp) { this.eventTimestamp = eventTimestamp; }

    // Nested class for order items
    public static class OrderItem {
        private String productId;
        private int quantity;
        private BigDecimal price;

        public String getProductId() { return productId; }
        public void setProductId(String productId) { this.productId = productId; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }
    }
}
