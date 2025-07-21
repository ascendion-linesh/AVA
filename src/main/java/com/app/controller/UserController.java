package com.app.controller;

import com.app.model.UserResponse;
import com.app.model.UpdateUserStatsRequest;
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
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Update user's totalOrders and totalSpent.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUserStats(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserStatsRequest request) {
        UserResponse updatedUser = userService.updateUserStats(id, request);
        return ResponseEntity.ok(updatedUser);
    }
}
