package com.example.rewards.model;

import java.math.BigDecimal;
import java.util.List;

public class RewardResponse {
    private BigDecimal discount;
    private List<String> appliedCampaigns;
    private List<String> loyaltyActions;
    private String message;

    public RewardResponse() {}
    public RewardResponse(BigDecimal discount, List<String> appliedCampaigns, List<String> loyaltyActions, String message) {
        this.discount = discount;
        this.appliedCampaigns = appliedCampaigns;
        this.loyaltyActions = loyaltyActions;
        this.message = message;
    }

    public BigDecimal getDiscount() { return discount; }
    public void setDiscount(BigDecimal discount) { this.discount = discount; }
    public List<String> getAppliedCampaigns() { return appliedCampaigns; }
    public void setAppliedCampaigns(List<String> appliedCampaigns) { this.appliedCampaigns = appliedCampaigns; }
    public List<String> getLoyaltyActions() { return loyaltyActions; }
    public void setLoyaltyActions(List<String> loyaltyActions) { this.loyaltyActions = loyaltyActions; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
