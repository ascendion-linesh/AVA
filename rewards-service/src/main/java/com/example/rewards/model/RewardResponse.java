package com.example.rewards.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Represents the response containing discounts and rewards for a cart evaluation.
 */
public class RewardResponse {
    private BigDecimal totalDiscount;
    private List<DiscountDetail> discounts;
    private List<LoyaltyReward> loyaltyRewards;

    public BigDecimal getTotalDiscount() { return totalDiscount; }
    public void setTotalDiscount(BigDecimal totalDiscount) { this.totalDiscount = totalDiscount; }
    public List<DiscountDetail> getDiscounts() { return discounts; }
    public void setDiscounts(List<DiscountDetail> discounts) { this.discounts = discounts; }
    public List<LoyaltyReward> getLoyaltyRewards() { return loyaltyRewards; }
    public void setLoyaltyRewards(List<LoyaltyReward> loyaltyRewards) { this.loyaltyRewards = loyaltyRewards; }

    public static class DiscountDetail {
        private String campaignName;
        private String description;
        private BigDecimal amount;

        public String getCampaignName() { return campaignName; }
        public void setCampaignName(String campaignName) { this.campaignName = campaignName; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public BigDecimal getAmount() { return amount; }
        public void setAmount(BigDecimal amount) { this.amount = amount; }
    }

    public static class LoyaltyReward {
        private String program;
        private int points;
        private String rewardType;

        public String getProgram() { return program; }
        public void setProgram(String program) { this.program = program; }
        public int getPoints() { return points; }
        public void setPoints(int points) { this.points = points; }
        public String getRewardType() { return rewardType; }
        public void setRewardType(String rewardType) { this.rewardType = rewardType; }
    }
}
