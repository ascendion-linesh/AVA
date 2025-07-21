package com.app.controller;

import com.app.model.ProfileDTO;
import com.app.model.UserStatsUpdateRequest;
import com.app.service.UserService;
import jakarta.validation.Valid;
lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for user-related endpoints.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Fetch user details by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> getUserById(@PathVariable Long id) {
        ProfileDTO userProfile = userService.getUserProfileById(id);
        return ResponseEntity.ok(userProfile);
    }

    /**
     * Update user's statistics such as totalOrders and totalSpent.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProfileDTO> updateUserStats(
            @PathVariable Long id,
            @RequestBody @Valid UserStatsUpdateRequest request) {
        ProfileDTO updatedProfile = userService.updateUserStats(id, request);
        return ResponseEntity.ok(updatedProfile);
    }
}
