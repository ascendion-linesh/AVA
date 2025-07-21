package com.app.talonone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

/**
 * TalonOneClient is a reusable, centralized client for interacting with the Talon.One Integration API.
 * <p>
 * It manages HTTP communication, authentication headers, and provides methods for updating user profiles,
 * evaluating sessions for rewards, and confirming loyalty points.
 * <p>
 * Configuration (base URL and API key) is loaded from application.properties.
 * <p>
 * Usage example:
 * <pre>
 *     talonOneClient.updateProfile(userId, profileDTO);
 *     RewardsResponse rewards = talonOneClient.evaluateSession(userId, sessionDTO);
 *     talonOneClient.confirmLoyalty(userId, totalAmount);
 * </pre>
 */
@Component
public class TalonOneClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final String apiKey;

    /**
     * Constructs a TalonOneClient with configuration loaded from application.properties.
     *
     * @param baseUrl Talon.One Integration API base URL (e.g., https://yourcompany.talon.one)
     * @param apiKey  Talon.One Integration API key (e.g., ApiKey-v1 ...)
     */
    public TalonOneClient(
            @Value("${talonone.base-url}") String baseUrl,
            @Value("${talonone.api-key}") String apiKey
    ) {
        this.restTemplate = new RestTemplate();
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        this.apiKey = apiKey;
    }

    /**
     * Updates a user's profile in Talon.One.
     *
     * @param userId     The unique identifier of the user.
     * @param profileDTO The profile data to update (DTO object).
     * @throws TalonOneClientException if the request fails.
     */
    public void updateProfile(String userId, Object profileDTO) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/v1/profiles/{userId}")
                .buildAndExpand(userId)
                .toUriString();

        HttpHeaders headers = createHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity = new HttpEntity<>(profileDTO, headers);

        try {
            restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
        } catch (HttpStatusCodeException ex) {
            throw handleException("Failed to update profile for userId: " + userId, ex);
        } catch (Exception ex) {
            throw new TalonOneClientException("Unexpected error updating profile for userId: " + userId, ex);
        }
    }

    /**
     * Evaluates a user's session in Talon.One and returns personalized rewards and discounts.
     *
     * @param userId     The unique identifier of the user (used for logging/tracing).
     * @param sessionDTO The session data to evaluate (DTO object).
     * @return RewardsResponse containing the evaluation result.
     * @throws TalonOneClientException if the request fails.
     */
    public RewardsResponse evaluateSession(String userId, Object sessionDTO) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/v1/sessions")
                .toUriString();

        HttpHeaders headers = createHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity = new HttpEntity<>(sessionDTO, headers);

        try {
            ResponseEntity<RewardsResponse> response = restTemplate.exchange(
                    url, HttpMethod.POST, entity, RewardsResponse.class
            );
            return response.getBody();
        } catch (HttpStatusCodeException ex) {
            throw handleException("Failed to evaluate session for userId: " + userId, ex);
        } catch (Exception ex) {
            throw new TalonOneClientException("Unexpected error evaluating session for userId: " + userId, ex);
        }
    }

    /**
     * Confirms a user's loyalty transaction in Talon.One.
     *
     * @param userId      The unique identifier of the user.
     * @param totalAmount The total amount to confirm for loyalty.
     * @throws TalonOneClientException if the request fails.
     */
    public void confirmLoyalty(String userId, double totalAmount) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/v1/loyalty/{userId}/confirm")
                .buildAndExpand(userId)
                .toUriString();

        HttpHeaders headers = createHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Assuming Talon.One expects a JSON body like {"totalAmount": ...}
        String body = String.format("{\"totalAmount\": %s}", totalAmount);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        try {
            restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
        } catch (HttpStatusCodeException ex) {
            throw handleException("Failed to confirm loyalty for userId: " + userId, ex);
        } catch (Exception ex) {
            throw new TalonOneClientException("Unexpected error confirming loyalty for userId: " + userId, ex);
        }
    }

    /**
     * Creates HTTP headers with Authorization for Talon.One API.
     *
     * @return HttpHeaders with Authorization header set.
     */
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "ApiKey-v1 " + apiKey);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

    /**
     * Handles HTTP exceptions and wraps them in a TalonOneClientException.
     *
     * @param message Contextual error message.
     * @param ex      The HttpStatusCodeException thrown by RestTemplate.
     * @return TalonOneClientException with details.
     */
    private TalonOneClientException handleException(String message, HttpStatusCodeException ex) {
        String errorBody = ex.getResponseBodyAsString();
        String fullMessage = String.format("%s. Status: %s, Body: %s", message, ex.getStatusCode(), errorBody);
        return new TalonOneClientException(fullMessage, ex);
    }

    /**
     * Exception class for TalonOneClient errors.
     */
    public static class TalonOneClientException extends RuntimeException {
        public TalonOneClientException(String message, Throwable cause) {
            super(message, cause);
        }
        public TalonOneClientException(String message) {
            super(message);
        }
    }

    /**
     * Placeholder for the RewardsResponse DTO.
     * Replace this with the actual RewardsResponse implementation as needed.
     */
    public static class RewardsResponse {
        // Define fields and methods as per actual Talon.One response schema.
    }
}
