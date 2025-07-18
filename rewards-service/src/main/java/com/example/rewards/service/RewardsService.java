package com.example.rewards.service;

import com.example.rewards.model.OrderEvent;
import com.example.rewards.model.RewardResponse;
import com.example.rewards.model.TalonOneSessionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RewardsService {
    private final TalonOneService talonOneService;

    @Autowired
    public RewardsService(TalonOneService talonOneService) {
        this.talonOneService = talonOneService;
    }

    public RewardResponse evaluateCart(OrderEvent orderEvent) {
        TalonOneSessionRequest sessionRequest = new TalonOneSessionRequest();
        sessionRequest.setCustomerId(orderEvent.getCustomerId());
        sessionRequest.setTotalAmount(orderEvent.getTotalAmount());
        // Convert items to Talon.One format
        List<Map<String, Object>> cartItems = new ArrayList<>();
        if (orderEvent.getItems() != null) {
            for (OrderEvent.OrderItem item : orderEvent.getItems()) {
                cartItems.add(Map.of(
                        "productId", item.getProductId(),
                        "quantity", item.getQuantity(),
                        "price", item.getPrice()
                ));
            }
        }
        sessionRequest.setCartItems(cartItems);
        // Optionally set sessionAttributes
        sessionRequest.setSessionAttributes(Map.of("eventType", orderEvent.getEventType()));

        Map<String, Object> talonResponse = talonOneService.evaluateSession(sessionRequest);
        // Parse Talon.One response
        BigDecimal discount = BigDecimal.ZERO;
        List<String> campaigns = new ArrayList<>();
        List<String> loyaltyActions = new ArrayList<>();
        if (talonResponse != null) {
            if (talonResponse.containsKey("effects")) {
                List<Map<String, Object>> effects = (List<Map<String, Object>>) talonResponse.get("effects");
                for (Map<String, Object> effect : effects) {
                    if ("setDiscount".equals(effect.get("effectType"))) {
                        discount = discount.add(new BigDecimal(String.valueOf(effect.get("value"))));
                    }
                    if ("addLoyaltyPoints".equals(effect.get("effectType"))) {
                        loyaltyActions.add("Added " + effect.get("value") + " points");
                    }
                }
            }
            if (talonResponse.containsKey("appliedCampaigns")) {
                List<Map<String, Object>> applied = (List<Map<String, Object>>) talonResponse.get("appliedCampaigns");
                for (Map<String, Object> camp : applied) {
                    campaigns.add((String) camp.get("name"));
                }
            }
        }
        return new RewardResponse(discount, campaigns, loyaltyActions, "Evaluation successful");
    }
}
