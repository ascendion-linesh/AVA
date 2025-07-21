package talonone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
talonone.dto.ProfileDTO;
talonone.dto.SessionDTO;
talonone.dto.RewardsResponse;

/**
 * TalonOneClient is a reusable and centralized client for interacting with Talon.One's Integration API.
 * <p>
 * It provides methods to update customer profiles, evaluate sessions for rewards/discounts,
 * and confirm loyalty point usage. The client handles HTTP communication, authentication headers,
 * and error handling, and is designed for use within a Spring Boot application.
 * </p>
 *
 * <p>
 * Configuration:
 * <ul>
 *   <li>talonone.base-url - The base URL for Talon.One's Integration API (set in application.properties).</li>
 *   <li>talonone.api-key  - The API key for authenticating requests (set in application.properties).</li>
 * </ul>
 * </p>
 *
 * <p>
 * Usage example:
 * <pre>
 *     @Autowired
 *     private TalonOneClient talonOneClient;
 *
 *     talonOneClient.updateProfile("user-123", profileDto);
 *     RewardsResponse rewards = talonOneClient.evaluateSession(sessionDto);
 *     talonOneClient.confirmLoyalty("user-123", 99.99);
 * </pre>
 * </p>
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
     * Updates the Talon.One profile for the specified user.
     *
     * @param userId the unique identifier of the user
     * @param dto    the profile data to update
     * @throws ResponseStatusException if the API call fails
     */
    public void updateProfile(String userId, ProfileDTO dto) {
        String url = String.format("%s/v1/profiles/%s", baseUrl, userId);
        HttpHeaders headers = createHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProfileDTO> entity = new HttpEntity<>(dto, headers);

        try {
            restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
        } catch (HttpStatusCodeException ex) {
            throw new ResponseStatusException(
                    ex.getStatusCode(),
                    String.format("Failed to update profile for userId=%s: %s", userId, ex.getResponseBodyAsString()),
                    ex
            );
        }
    }

    /**
     * Evaluates a session for rewards and discounts using Talon.One.
     *
     * @param dto the session data to evaluate
     * @return RewardsResponse containing applicable discounts and rewards
     * @throws ResponseStatusException if the API call fails
     */
    public RewardsResponse evaluateSession(SessionDTO dto) {
        String url = String.format("%s/v1/sessions", baseUrl);
        HttpHeaders headers = createHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SessionDTO> entity = new HttpEntity<>(dto, headers);

        try {
            ResponseEntity<RewardsResponse> response = restTemplate.exchange(
                    url, HttpMethod.POST, entity, RewardsResponse.class);
            return response.getBody();
        } catch (HttpStatusCodeException ex) {
            throw new ResponseStatusException(
                    ex.getStatusCode(),
                    String.format("Failed to evaluate session: %s", ex.getResponseBodyAsString()),
                    ex
            );
        }
    }

    /**
     * Confirms loyalty point usage for a user after an order is placed.
     *
     * @param userId      the unique identifier of the user
     * @param totalAmount the total amount of the order
     * @throws ResponseStatusException if the API call fails
     */
    public void confirmLoyalty(String userId, double totalAmount) {
        String url = String.format("%s/v1/loyalty/%s/confirm", baseUrl, userId);
        HttpHeaders headers = createHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Assuming the API expects a JSON body with the total amount
        String body = String.format("{\"totalAmount\": %.2f}", totalAmount);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        try {
            restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
        } catch (HttpStatusCodeException ex) {
            throw new ResponseStatusException(
                    ex.getStatusCode(),
                    String.format("Failed to confirm loyalty for userId=%s: %s", userId, ex.getResponseBodyAsString()),
                    ex
            );
        }
    }

    /**
     * Creates HTTP headers with the Authorization header set to the Talon.One API key.
     *
     * @return HttpHeaders with Authorization
     */
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        return headers;
    }
}
