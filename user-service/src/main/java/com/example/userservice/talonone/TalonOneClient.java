package com.example.userservice.talonone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles integration with Talon.One Campaign Rules Engine.
 */
@Component
public class TalonOneClient {
    @Value("${talonone.api.url}")
    private String talonOneApiUrl;

    @Value("${talonone.api.key}")
    private String talonOneApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Registers a user in Talon.One (example implementation).
     * @param userId User's unique ID
     * @param email User's email
     * @param name User's name
     */
    public void registerUserInTalonOne(Long userId, String email, String name) {
        String url = talonOneApiUrl + "/v1/customers";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "ApiKey " + talonOneApiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("integrationId", userId.toString());
        body.put("attributes", Map.of("email", email, "name", name));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        try {
            restTemplate.postForEntity(url, request, String.class);
        } catch (Exception e) {
            // Log error or handle as needed
            System.err.println("Talon.One registration failed: " + e.getMessage());
        }
    }
}
