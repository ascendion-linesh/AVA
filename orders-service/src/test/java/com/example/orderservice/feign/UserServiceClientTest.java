package com.example.orderservice.feign;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = UserServiceClient.class)
class UserServiceClientTest {
    @Autowired(required = false)
    private UserServiceClient userServiceClient;

    @Test
    void contextLoads() {
        assertThat(userServiceClient).isNotNull();
    }
}
