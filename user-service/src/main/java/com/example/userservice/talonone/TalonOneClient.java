package com.example.userservice.talonone;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class TalonOneClient {
    @Value("${talonone.api.url}")
    private String talonOneApiUrl;

    @Value("${talonone.api.key}")
    private String talonOneApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public void registerUserInTalonOne(String userId, String email, String name, String phone) {
        String url = talonOneApiUrl + "/v1/customers";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "ApiKey " + talonOneApiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("integrationId", userId);
        body.put("attributes", Map.of(
                "email", email,
                "name", name,
                "phone", phone
        ));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                log.error("Talon.One registration failed: {}", response.getBody());
                throw new RuntimeException("Failed to register user in Talon.One");
            }
        } catch (HttpClientErrorException e) {
            log.error("Talon.One registration error: {}", e.getResponseBodyAsString());
            throw new RuntimeException("Talon.One registration error: " + e.getMessage());
        }
    }
}
