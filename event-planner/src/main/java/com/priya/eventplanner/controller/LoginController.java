package com.priya.eventplanner.controller;

import com.priya.eventplanner.dto.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * Controller providing authentication-related endpoints:
 * login, signup, and password reset.
 */
@RestController
@RequestMapping("/auth")
public class LoginController {

    /**
     * Authenticate a user with username and password.
     */
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password) {
        return "Login successful for " + username + ".";
    }

    /**
     * Register a new user using details provided in the request body.
     *
     * @param userDetails the user signup details
     * @return success message with the registered username
     */
    @PostMapping("/signup")
    public String signup(@RequestBody UserDetails userDetails) {
        // TODO: Save userDetails to database
        return "Signup successful for " + userDetails.getUsername() + ".";
    }

    /**
     * Reset password using security question and answer.
     */
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String username,
                                 @RequestParam String securityQuestion,
                                 @RequestParam String answer,
                                 @RequestParam String newPassword) {
        return "Password successfully reset for " + username + ".";
    }
}