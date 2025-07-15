package com.hireboost.controller;

import com.hireboost.model.User;
import com.hireboost.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users API", description = "Operations for managing users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get currently authenticated user")
    @GetMapping("/current")
    public User getCurrentUser() {
        log.info("Fetching current authenticated user");
        return userService.getCurrentUser();
    }

    @Operation(summary = "Get user by username")
    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {
        log.info("Fetching user with username '{}'", username);
        return userService.getUserByUserName(username);
    }

    @Operation(summary = "Promote current user to admin (dev only)")
    @PostMapping("/promote-admin")
    public void promoteToAdmin() {
        log.warn("Promoting current user to admin");
        userService.promoteCurrentUserToAdmin();
    }
}
