package com.app.controller;

import com.app.model.CartRequest;
import com.app.model.RewardsResponse;
import com.app.service.RewardsService;
import jakarta.validation.Valid;
lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for rewards evaluation endpoints.
 */
@RestController
@RequestMapping("/rewards")
@RequiredArgsConstructor
public class RewardsController {

    private final RewardsService rewardsService;

    /**
     * Evaluate rewards for a given cart.
     */
    @PostMapping("/evaluate")
    public ResponseEntity<RewardsResponse> evaluateRewards(@Valid @RequestBody CartRequest cartRequest) {
        RewardsResponse rewardsResponse = rewardsService.evaluateRewards(cartRequest);
        return ResponseEntity.ok(rewardsResponse);
    }
}
