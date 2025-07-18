package com.example.userservice.talonone;

import com.example.userservice.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

@Component
public class TalonOneClient {
    private static final Logger logger = LoggerFactory.getLogger(TalonOneClient.class);

    @Value("${talonone.api.url}")
    private String talonOneApiUrl;

    @Value("${talonone.api.key}")
    private String talonOneApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public void registerUserInTalonOne(User user) {
        try {
            String url = talonOneApiUrl + "/v1/customers";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "ApiKey " + talonOneApiKey);

            Map<String, Object> body = new HashMap<>();
            body.put("integrationId", user.getId().toString());
            body.put("email", user.getEmail());
            body.put("name", user.getName());
            body.put("phone", user.getPhone());

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            logger.info("Talon.One registration response: {}", response.getStatusCode());
        } catch (Exception e) {
            logger.error("Failed to register user in Talon.One: {}", e.getMessage());
            // Optionally, handle retry or compensation logic here
        }
    }
}
