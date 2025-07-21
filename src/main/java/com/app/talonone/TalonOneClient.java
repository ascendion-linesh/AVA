package com.app.talonone;

import com.app.model.ProfileDTO;
import com.app.model.RewardsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * Client for interacting with the Talon.One Integration API.
 * <p>
 * Provides methods to update customer profiles, evaluate sessions for rewards,
 * and confirm loyalty transactions. Handles HTTP communication, authentication,
 * and error handling according to Spring Boot best practices.
 * </p>
 *
 * <pre>
 * Example usage:
 *   talonOneClient.updateProfile("user123", profileDto);
 *   RewardsResponse rewards = talonOneClient.evaluateSession(sessionDto);
 *   talonOneClient.confirmLoyalty("user123", 150.0);
 * </pre>
 */
@Component
public class TalonOneClient {

    @Value("${talonone.base-url}")
    private String baseUrl;

    @Value("${talonone.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    /**
     * Constructs a TalonOneClient with the provided RestTemplate.
     *
     * @param restTemplate the RestTemplate to use for HTTP requests
     */
    public TalonOneClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Updates the profile of a user in Talon.One.
     *
     * @param userId the unique identifier of the user
     * @param dto    the profile data to update
     * @throws TalonOneClientException if the request fails
     */
    public void updateProfile(String userId, ProfileDTO dto) {
        String url = String.format("%s/v1/profiles/%s", baseUrl, userId);
        HttpHeaders headers = createHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProfileDTO> entity = new HttpEntity<>(dto, headers);

        try {
            restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            throw new TalonOneClientException("Failed to update profile: " + ex.getResponseBodyAsString(), ex);
        } catch (RestClientException ex) {
            throw new TalonOneClientException("Failed to update profile: " + ex.getMessage(), ex);
        }
    }

    /**
     * Evaluates a session in Talon.One to determine applicable rewards and discounts.
     *
     * @param dto the session data
     * @return the rewards response from Talon.One
     * @throws TalonOneClientException if the request fails
     */
    public RewardsResponse evaluateSession(Object dto) {
        String url = String.format("%s/v1/sessions", baseUrl);
        HttpHeaders headers = createHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(dto, headers);

        try {
            ResponseEntity<RewardsResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    RewardsResponse.class
            );
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new TalonOneClientException("Unexpected response from Talon.One: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            throw new TalonOneClientException("Failed to evaluate session: " + ex.getResponseBodyAsString(), ex);
        } catch (RestClientException ex) {
            throw new TalonOneClientException("Failed to evaluate session: " + ex.getMessage(), ex);
        }
    }

    /**
     * Confirms a loyalty transaction for a user in Talon.One.
     *
     * @param userId      the unique identifier of the user
     * @param totalAmount the total amount for loyalty confirmation
     * @throws TalonOneClientException if the request fails
     */
    public void confirmLoyalty(String userId, double totalAmount) {
        String url = String.format("%s/v1/loyalty/%s/confirm", baseUrl, userId);
        HttpHeaders headers = createHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Assuming the API expects a JSON body with totalAmount
        String body = String.format("{\"totalAmount\": %.2f}", totalAmount);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        try {
            restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            throw new TalonOneClientException("Failed to confirm loyalty: " + ex.getResponseBodyAsString(), ex);
        } catch (RestClientException ex) {
            throw new TalonOneClientException("Failed to confirm loyalty: " + ex.getMessage(), ex);
        }
    }

    /**
     * Creates HTTP headers with Authorization for Talon.One API.
     *
     * @return HttpHeaders with Authorization set
     */
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "ApiKey-v1 " + apiKey);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
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
}
