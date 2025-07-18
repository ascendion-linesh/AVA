package com.ascendion.ava.orderservice.client;

import com.ascendion.ava.orderservice.dto.DiscountRequestDto;
import com.ascendion.ava.orderservice.dto.DiscountResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "rewards-service", url = "http://localhost:8082")
public interface RewardsServiceClient {
    @PostMapping("/rewards/discount")
    DiscountResponseDto calculateDiscount(@RequestBody DiscountRequestDto discountRequest);
}
