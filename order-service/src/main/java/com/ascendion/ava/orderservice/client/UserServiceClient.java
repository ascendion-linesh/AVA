package com.ascendion.ava.orderservice.client;

import com.ascendion.ava.orderservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "user-service", url = "http://localhost:8080")
public interface UserServiceClient {
    @GetMapping("/users/{userId}")
    UserDto getUserById(@PathVariable("userId") UUID userId);
}
