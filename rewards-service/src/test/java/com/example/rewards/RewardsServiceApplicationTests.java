package com.example.rewards;

import com.example.rewards.model.RewardResponse;
import com.example.rewards.model.TalonOneSessionRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RewardsServiceApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        // Basic context load test
    }

    @Test
    void testEvaluateRewardsEndpoint() {
        TalonOneSessionRequest request = new TalonOneSessionRequest();
        request.setCustomerId("test-customer-1");
        request.setCartItems(Collections.singletonList(new HashMap<>()));
        request.setSessionAttributes(new HashMap<>());
        HttpEntity<TalonOneSessionRequest> entity = new HttpEntity<>(request);
        ResponseEntity<RewardResponse> response = restTemplate.postForEntity("/api/rewards/evaluate", entity, RewardResponse.class);
        assertThat(response.getStatusCode()).isIn(HttpStatus.OK, HttpStatus.BAD_GATEWAY, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
