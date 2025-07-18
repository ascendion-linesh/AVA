package com.example.rewards.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class TalonOneSessionRequest {
    private String customerId;
    private List<Map<String, Object>> cartItems;
    private BigDecimal totalAmount;
    private Map<String, Object> sessionAttributes;

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public List<Map<String, Object>> getCartItems() { return cartItems; }
    public void setCartItems(List<Map<String, Object>> cartItems) { this.cartItems = cartItems; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public Map<String, Object> getSessionAttributes() { return sessionAttributes; }
    public void setSessionAttributes(Map<String, Object> sessionAttributes) { this.sessionAttributes = sessionAttributes; }
}
