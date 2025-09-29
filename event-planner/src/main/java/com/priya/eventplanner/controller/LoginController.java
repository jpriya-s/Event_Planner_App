package com.priya.eventplanner.controller;

import com.priya.eventplanner.dto.UserDetailsDTO;
import com.priya.eventplanner.service.LoginService;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for authentication-related endpoints.
 */
@RestController
@RequestMapping("/auth")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * Authenticate a user with username and password.
     *
     * @param username the username
     * @param password the password
     * @return success or error message
     */
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password) {
        return loginService.login(username, password);
    }

    /**
     * Register a new user using details provided in the request body.
     *
     * @param userDetailsDTO the user signup details
     * @return success message if registration is successful
     */
    @PostMapping("/signup")
    public String signup(@RequestBody UserDetailsDTO userDetailsDTO) {
        return loginService.signup(userDetailsDTO);
    }

    /**
     * Reset password using security question and answer.
     *
     * @param username the username of the account
     * @param securityQuestion the security question
     * @param answer the answer to the security question
     * @param newPassword the new password to set
     * @return success or error message
     */
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String username,
                                @RequestParam String securityQuestion,
                                @RequestParam String answer,
                                @RequestParam String newPassword) {
        return loginService.resetPassword(username, securityQuestion, answer, newPassword);
    }
}