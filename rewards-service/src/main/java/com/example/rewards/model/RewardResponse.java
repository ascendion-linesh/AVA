package com.example.rewards.model;

import java.util.List;

public class RewardResponse {
    private String customerId;
    private double discountAmount;
    private List<String> appliedRewards;
    private String message;

    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public double getDiscountAmount() {
        return discountAmount;
    }
    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }
    public List<String> getAppliedRewards() {
        return appliedRewards;
    }
    public void setAppliedRewards(List<String> appliedRewards) {
        this.appliedRewards = appliedRewards;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RewardResponse{" +
                "customerId='" + customerId + '\'' +
                ", discountAmount=" + discountAmount +
                ", appliedRewards=" + appliedRewards +
                ", message='" + message + '\'' +
                '}';
    }
}
