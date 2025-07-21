package com.example.rewards.service;

import com.example.rewards.config.TalonOneConfig;
import com.example.rewards.exception.TalonOneException;
import com.example.rewards.model.RewardResponse;
import com.example.rewards.model.TalonOneSessionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class TalonOneService {
    private static final Logger logger = LoggerFactory.getLogger(TalonOneService.class);

    private final TalonOneConfig talonOneConfig;
    private final RestTemplate restTemplate;

    @Autowired
    public TalonOneService(TalonOneConfig talonOneConfig) {
        this.talonOneConfig = talonOneConfig;
        this.restTemplate = new RestTemplate();
    }

    public RewardResponse evaluateSession(TalonOneSessionRequest request) {
        String url = talonOneConfig.getBaseUrl() + "/v1/customer_sessions";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "ApiKey-v1 " + talonOneConfig.getApiKey());
        headers.set("X-Talon-Application", talonOneConfig.getApplicationId());
        HttpEntity<TalonOneSessionRequest> entity = new HttpEntity<>(request, headers);
        try {
            logger.debug("Sending request to Talon.One: {}", request);
            ResponseEntity<RewardResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    RewardResponse.class
            );
            logger.debug("Received response from Talon.One: {}", response.getBody());
            return response.getBody();
        } catch (HttpStatusCodeException e) {
            logger.error("Talon.One API error: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new TalonOneException("Talon.One API error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            logger.error("Unexpected error calling Talon.One API", e);
            throw new TalonOneException("Unexpected error calling Talon.One API", e);
        }
    }
}
