package talonone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * TalonOneClient is a reusable and centralized client for interacting with Talon.One's Integration API.
 * It provides methods for updating customer profiles, evaluating sessions for rewards, and confirming loyalty points.
 * <p>
 * The client is designed following Spring Boot best practices, ensuring clean, maintainable, and testable code.
 * Configuration properties such as the base URL and API key are loaded from application.properties.
 * </p>
 *
 * <h3>Usage Example:</h3>
 * <pre>
 * {@code
 * @Autowired
 * private TalonOneClient talonOneClient;
 *
 * // Update profile
 * talonOneClient.updateProfile("user-123", profileDTO);
 *
 * // Evaluate session
 * RewardsResponse response = talonOneClient.evaluateSession(sessionDTO);
 *
 * // Confirm loyalty
 * talonOneClient.confirmLoyalty("user-123", 150.0);
 * }
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
     * @param restTemplate the RestTemplate to use for HTTP communication
     */
    public TalonOneClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Updates the profile of a user in Talon.One.
     *
     * @param userId the unique identifier of the user
     * @param dto    the profile data transfer object containing user attributes
     * @throws TalonOneClientException if the API call fails
     */
    public void updateProfile(String userId, ProfileDTO dto) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/v1/profiles/{userId}")
                .buildAndExpand(userId)
                .toUriString();

        HttpHeaders headers = createHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProfileDTO> entity = new HttpEntity<>(dto, headers);

        try {
            restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
        } catch (HttpStatusCodeException ex) {
            throw new TalonOneClientException("Failed to update profile: " + ex.getResponseBodyAsString(), ex);
        }
    }

    /**
     * Evaluates a session for rewards and discounts in Talon.One.
     *
     * @param dto the session data transfer object containing session/cart details
     * @return RewardsResponse containing personalized rewards and discounts
     * @throws TalonOneClientException if the API call fails
     */
    public RewardsResponse evaluateSession(SessionDTO dto) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/v1/sessions")
                .toUriString();

        HttpHeaders headers = createHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SessionDTO> entity = new HttpEntity<>(dto, headers);

        try {
            ResponseEntity<RewardsResponse> response = restTemplate.exchange(
                    url, HttpMethod.POST, entity, RewardsResponse.class);
            return response.getBody();
        } catch (HttpStatusCodeException ex) {
            throw new TalonOneClientException("Failed to evaluate session: " + ex.getResponseBodyAsString(), ex);
        }
    }

    /**
     * Confirms the usage of loyalty points for a user after a successful transaction.
     *
     * @param userId      the unique identifier of the user
     * @param totalAmount the total amount for which loyalty points are being confirmed
     * @throws TalonOneClientException if the API call fails
     */
    public void confirmLoyalty(String userId, double totalAmount) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/v1/loyalty/{userId}/confirm")
                .buildAndExpand(userId)
                .toUriString();

        HttpHeaders headers = createHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Assuming the API expects a JSON body like: { "totalAmount": 123.45 }
        ConfirmLoyaltyRequest body = new ConfirmLoyaltyRequest(totalAmount);
        HttpEntity<ConfirmLoyaltyRequest> entity = new HttpEntity<>(body, headers);

        try {
            restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
        } catch (HttpStatusCodeException ex) {
            throw new TalonOneClientException("Failed to confirm loyalty: " + ex.getResponseBodyAsString(), ex);
        }
    }

    /**
     * Creates HTTP headers with the Authorization header set for Talon.One API.
     *
     * @return HttpHeaders with Authorization
     */
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "ApiKey-v1 " + apiKey);
        return headers;
    }

    /**
     * Exception class for TalonOneClient errors.
     */
    public static class TalonOneClientException extends RuntimeException {
        public TalonOneClientException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * DTO for confirm loyalty request body.
     */
    public static class ConfirmLoyaltyRequest {
        private double totalAmount;

        public ConfirmLoyaltyRequest() {
        }

        public ConfirmLoyaltyRequest(double totalAmount) {
            this.totalAmount = totalAmount;
        }

        public double getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
        }
    }

    // --- Placeholders for DTOs (replace with actual imports in your project) ---

    /**
     * Placeholder for the ProfileDTO class.
     * Replace with your actual implementation or import.
     */
    public static class ProfileDTO {
        // Define fields and methods as per your application's requirements
    }

    /**
     * Placeholder for the SessionDTO class.
     * Replace with your actual implementation or import.
     */
    public static class SessionDTO {
        // Define fields and methods as per your application's requirements
    }

    /**
     * Placeholder for the RewardsResponse class.
     * Replace with your actual implementation or import.
     */
    public static class RewardsResponse {
        // Define fields and methods as per your application's requirements
    }
}
