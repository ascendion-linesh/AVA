package com.app.controller;

import com.app.model.RewardsEvaluationRequest;
import com.app.model.RewardsResponse;
import com.app.service.RewardsService;
import jakarta.validation.Valid;
lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rewards")
@RequiredArgsConstructor
public class RewardsController {

    private final RewardsService rewardsService;

    /**
     * Evaluate rewards and discounts for a given cart.
     */
    @PostMapping("/evaluate")
    public ResponseEntity<RewardsResponse> evaluateRewards(
            @RequestBody @Valid RewardsEvaluationRequest request) {
        RewardsResponse response = rewardsService.evaluateRewards(request);
        return ResponseEntity.ok(response);
    }
}
