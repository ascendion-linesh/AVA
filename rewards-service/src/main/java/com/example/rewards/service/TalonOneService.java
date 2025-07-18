package com.example.rewards.service;

import com.example.rewards.config.TalonOneConfig;
import com.example.rewards.exception.TalonOneException;
import com.example.rewards.model.TalonOneSessionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

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

    public Map<String, Object> evaluateSession(TalonOneSessionRequest request) {
        String url = talonOneConfig.getApiUrl() + "/v1/customer_sessions";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "ApiKey-v1 " + talonOneConfig.getApiKey());
        HttpEntity<TalonOneSessionRequest> entity = new HttpEntity<>(request, headers);
        try {
            logger.info("Calling Talon.One API at {}", url);
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                logger.error("Talon.One API returned status: {}", response.getStatusCode());
                throw new TalonOneException("Talon.One API error: " + response.getStatusCode());
            }
        } catch (RestClientException ex) {
            logger.error("Talon.One API call failed", ex);
            throw new TalonOneException("Talon.One API call failed", ex);
        }
    }
}
