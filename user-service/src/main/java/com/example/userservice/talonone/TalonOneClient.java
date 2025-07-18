package com.example.userservice.talonone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles communication with Talon.One API for user registration.
 */
@Component
public class TalonOneClient {
    @Value("${talonone.api.url}")
    private String talonOneApiUrl;

    @Value("${talonone.api.key}")
    private String talonOneApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Registers a user in Talon.One (example: create customer profile).
     * @param userId User ID
     * @param email User email
     * @param name User name
     */
    public void registerUserWithTalonOne(Long userId, String email, String name) {
        String url = UriComponentsBuilder.fromHttpUrl(talonOneApiUrl)
                .path("/v1/customer_profiles/" + userId)
                .toUriString();

        Map<String, Object> body = new HashMap<>();
        body.put("attributes", Map.of("email", email, "name", name));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "ApiKey " + talonOneApiKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        try {
            restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
        } catch (Exception ex) {
            // Log error and proceed (do not block user registration)
            System.err.println("Talon.One registration failed: " + ex.getMessage());
        }
    }
}
