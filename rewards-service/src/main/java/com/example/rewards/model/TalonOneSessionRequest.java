package com.example.rewards.model;

import java.util.List;
import java.util.Map;

public class TalonOneSessionRequest {
    private String customerId;
    private List<Map<String, Object>> cartItems;
    private Map<String, Object> sessionAttributes;

    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public List<Map<String, Object>> getCartItems() {
        return cartItems;
    }
    public void setCartItems(List<Map<String, Object>> cartItems) {
        this.cartItems = cartItems;
    }
    public Map<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }
    public void setSessionAttributes(Map<String, Object> sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

    @Override
    public String toString() {
        return "TalonOneSessionRequest{" +
                "customerId='" + customerId + '\'' +
                ", cartItems=" + cartItems +
                ", sessionAttributes=" + sessionAttributes +
                '}';
    }
}
