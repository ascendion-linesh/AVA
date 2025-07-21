package com.example.rewards.model;

import java.time.Instant;

public class OrderEvent {
    private String orderId;
    private String customerId;
    private double orderTotal;
    private Instant orderTimestamp;
    private String status;

    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public double getOrderTotal() {
        return orderTotal;
    }
    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }
    public Instant getOrderTimestamp() {
        return orderTimestamp;
    }
    public void setOrderTimestamp(Instant orderTimestamp) {
        this.orderTimestamp = orderTimestamp;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderEvent{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", orderTotal=" + orderTotal +
                ", orderTimestamp=" + orderTimestamp +
                ", status='" + status + '\'' +
                '}';
    }
}
