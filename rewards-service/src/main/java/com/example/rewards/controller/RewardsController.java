package com.example.rewards.controller;

import com.example.rewards.model.OrderEvent;
import com.example.rewards.model.RewardResponse;
import com.example.rewards.service.RewardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody as SpringRequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rewards")
public class RewardsController {
    private final RewardsService rewardsService;

    @Autowired
    public RewardsController(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    @Operation(summary = "Evaluate cart for discounts and rewards", description = "Evaluates the cart and returns applicable discounts and loyalty rewards.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Discounts and rewards evaluated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RewardResponse.class))),
        @ApiResponse(responseCode = "502", description = "Talon.One API error", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping(value = "/evaluate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RewardResponse> evaluateCart(
            @SpringRequestBody(description = "Order event or cart data", required = true, content = @Content(schema = @Schema(implementation = OrderEvent.class)))
            @org.springframework.web.bind.annotation.RequestBody OrderEvent orderEvent) {
        RewardResponse response = rewardsService.evaluateCart(orderEvent);
        return ResponseEntity.ok(response);
    }
}
