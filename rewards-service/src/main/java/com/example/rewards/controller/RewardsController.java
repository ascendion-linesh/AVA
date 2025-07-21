package com.example.rewards.controller;

import com.example.rewards.model.RewardResponse;
import com.example.rewards.model.TalonOneSessionRequest;
import com.example.rewards.service.RewardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rewards")
public class RewardsController {
    private static final Logger logger = LoggerFactory.getLogger(RewardsController.class);

    private final RewardsService rewardsService;

    @Autowired
    public RewardsController(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    @Operation(summary = "Evaluate cart session for rewards and discounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evaluation successful",
                    content = @Content(schema = @Schema(implementation = RewardResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/evaluate")
    public ResponseEntity<RewardResponse> evaluateCartSession(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Cart session data for evaluation",
                    required = true,
                    content = @Content(schema = @Schema(implementation = TalonOneSessionRequest.class))
            )
            @RequestBody TalonOneSessionRequest request) {
        logger.info("Received request to evaluate rewards for customerId: {}", request.getCustomerId());
        RewardResponse response = rewardsService.evaluateSession(request);
        logger.info("Evaluation completed for customerId: {}. Response: {}", request.getCustomerId(), response);
        return ResponseEntity.ok(response);
    }
}
