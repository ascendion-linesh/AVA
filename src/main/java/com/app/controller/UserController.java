package com.app.controller;

import com.app.model.User;
import com.app.model.ProfileDTO;
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
     * @param id User ID
     * @return User details or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> getUserById(@PathVariable Long id) {
        ProfileDTO user = userService.getUserProfileById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * Update user's totalOrders and totalSpent.
     * @param id User ID
     * @param profileDTO DTO containing updated stats
     * @return 204 No Content if updated, 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUserStats(
            @PathVariable Long id,
            @Valid @RequestBody ProfileDTO profileDTO
    ) {
        boolean updated = userService.updateUserStats(id, profileDTO);
        if (!updated) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
