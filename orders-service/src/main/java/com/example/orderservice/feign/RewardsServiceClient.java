package com.example.orderservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.Map;

@FeignClient(name = "rewards-service", url = "http://localhost:8082")
public interface RewardsServiceClient {
    @PostMapping("/rewards/calculate-discount")
    Map<String, BigDecimal> calculateDiscount(@RequestBody Map<String, Object> payload);
}
