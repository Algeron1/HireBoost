package com.hireboost.service;

import com.hireboost.exception.UserAlreadyExistsException;
import com.hireboost.exception.UserNotFoundException;
import com.hireboost.model.User;
import com.hireboost.model.enums.UserRole;
import com.hireboost.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private User save(User user) {
        return userRepository.save(user);
    }

    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            log.error("User with username '{}' already exists", user.getUsername());
            throw new UserAlreadyExistsException("A user with this username already exists: " + user.getUsername());
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            log.error("User with email '{}' already exists", user.getEmail());
            throw new UserAlreadyExistsException("A user with this email already exists: " + user.getEmail());
        }
        log.info("Creating new user with username '{}'", user.getUsername());
        return save(user);
    }

    public User getUserByUserName(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User '{}' not found in database", username);
                    return new UserNotFoundException("User not found: " + username);
                });
    }

    public UserDetailsService userDetailsService() {
        return this::getUserByUserName;
    }

    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Retrieving current user from SecurityContext: '{}'", username);
        return getUserByUserName(username);
    }

    /**
     * Deprecated: for testing or dev-only use.
     * Assigns admin role to the current user.
     */
    @Deprecated
    public void promoteCurrentUserToAdmin() {
        User user = getCurrentUser();
        log.warn("Promoting user '{}' to ROLE_ADMIN", user.getUsername());
        user.setRole(UserRole.ROLE_ADMIN);
        save(user);
    }
}
