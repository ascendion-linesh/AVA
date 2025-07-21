package com.app.controller;

import com.app.model.User;
import com.app.model.UserUpdateRequest;
import com.app.model.UserResponse;
import com.app.service.UserService;
jakarta.validation.Valid;
lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * Update user's totalOrders and totalSpent.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUserStats(
            @PathVariable Long id,
            @RequestBody @Valid UserUpdateRequest request) {
        UserResponse updatedUser = userService.updateUserStats(id, request);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }
}
