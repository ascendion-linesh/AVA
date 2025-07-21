package com.app.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;

/**
 * Configuration class for RestTemplate used to communicate with Talon.One's Integration API.
 * <p>
 * This configuration ensures:
 * <ul>
 *   <li>Singleton, thread-safe RestTemplate instance for TalonOneClient</li>
 *   <li>Secure injection of the Talon.One API key via application properties</li>
 *   <li>Concise logging of HTTP method and URL (no sensitive data)</li>
 *   <li>Automatic attachment of Authorization header for all outgoing requests</li>
 * </ul>
 * </p>
 *
 * <h3>Usage:</h3>
 * <pre>
 * &#64;Autowired
 * private RestTemplate restTemplate;
 * </pre>
 *
 * The RestTemplate bean defined here is intended for use in the TalonOneClient class.
 */
@Configuration
public class RestTemplateConfig {

    /**
     * The API key for authenticating with Talon.One's Integration API.
     * Injected securely from application properties.
     */
    @Value("${talonone.api-key}")
    private String talonOneApiKey;

    private static final Logger logger = LoggerFactory.getLogger(RestTemplateConfig.class);

    /**
     * Defines a singleton, thread-safe RestTemplate bean configured for Talon.One API.
     *
     * @return configured RestTemplate instance
     */
    @Bean
    public RestTemplate talonOneRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(
                Collections.singletonList(new TalonOneApiInterceptor(talonOneApiKey))
        );
        return restTemplate;
    }

    /**
     * ClientHttpRequestInterceptor that:
     * <ul>
     *   <li>Logs HTTP method and URL for each outgoing request</li>
     *   <li>Attaches the Authorization header with the Talon.One API key</li>
     * </ul>
     * <b>Note:</b> Does not log or expose sensitive information.
     */
    private static class TalonOneApiInterceptor implements ClientHttpRequestInterceptor {

        private final String apiKey;

        public TalonOneApiInterceptor(String apiKey) {
            this.apiKey = apiKey;
        }

        @Override
        public ClientHttpResponse intercept(
                ClientHttpRequest request,
                byte[] body,
                ClientHttpRequestExecution execution
        ) throws IOException {
            // Attach Authorization header
            request.getHeaders().set("Authorization", "ApiKey-v1 " + apiKey);

            // Log HTTP method and URL (no sensitive data)
            logger.info("Talon.One API Request - Method: {}, URL: {}", request.getMethod(), request.getURI());

            return execution.execute(request, body);
        }
    }
}
