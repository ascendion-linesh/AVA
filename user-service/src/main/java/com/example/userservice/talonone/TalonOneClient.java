package com.example.userservice.talonone;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpStatusCodeException;

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

    public void createCustomerProfile(String integrationId, String name, String email, String phone) {
        String url = talonOneApiUrl + "/v1/customer_profiles";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "ApiKey " + talonOneApiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("integrationId", integrationId);
        body.put("attributes", Map.of(
                "name", name,
                "email", email,
                "phone", phone
        ));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            log.info("Talon.One profile creation response: {}", response.getStatusCode());
        } catch (HttpStatusCodeException ex) {
            log.error("Talon.One API error: {} - {}", ex.getStatusCode(), ex.getResponseBodyAsString());
            throw new RuntimeException("Failed to create Talon.One customer profile");
        } catch (Exception ex) {
            log.error("Talon.One integration error: {}", ex.getMessage());
            throw new RuntimeException("Failed to integrate with Talon.One");
        }
    }
}
