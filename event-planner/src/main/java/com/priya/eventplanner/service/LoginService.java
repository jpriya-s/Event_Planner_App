package com.priya.eventplanner.service;

import com.priya.eventplanner.dto.UserDetailsDTO;
import com.priya.eventplanner.model.NotificationMethod;
import com.priya.eventplanner.model.UserDetails;
import com.priya.eventplanner.repository.UserDetailsRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class that handles business logic for login, signup, and password reset.
 */
@Service
public class LoginService {

    private final UserDetailsRepository userDetailsRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginService(UserDetailsRepository userDetailsRepository, PasswordEncoder passwordEncoder) {
        this.userDetailsRepository = userDetailsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Authenticate user using username and password.
     */
    public String login(String username, String password) {
        Optional<UserDetails> userOpt = userDetailsRepository.findByUsername(username);

        if (userOpt.isPresent()) {
            UserDetails user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return "Login successful for " + username + ".";
            }
            return "Invalid password.";
        }
        return "User not found.";
    }

    /**
     * Register a new user.
     *
     * @param userDetailsDTO the signup request
     * @return success message
     */
    public String signup(UserDetailsDTO userDetailsDTO) {
        if (userDetailsRepository.findByUsername(userDetailsDTO.getUsername()).isPresent()) {
            return "Username already exists.";
        }

        String hashedPassword = passwordEncoder.encode(userDetailsDTO.getPassword());

        // Convert DTO → Entity
        UserDetails user = UserDetails.builder()
                .username(userDetailsDTO.getUsername())
                .password(hashedPassword)
                .email(userDetailsDTO.getEmail())
                .mobileNumber(userDetailsDTO.getMobileNumber())
                .firstName(userDetailsDTO.getFirstName())
                .lastName(userDetailsDTO.getLastName())
                .securityQuestion(userDetailsDTO.getSecurityQuestion())
                .answer(userDetailsDTO.getAnswer())
                .notificationMethod(NotificationMethod.builder()
                        .methodName(userDetailsDTO.getNotificationMethod())
                        .build())
                .build();

        userDetailsRepository.save(user);
        return "Signup successful for " + userDetailsDTO.getUsername() + ".";
    }

    /**
     * Reset password using security question and answer.
     */
    public String resetPassword(String username, String securityQuestion,
                                String answer, String newPassword) {
        Optional<UserDetails> userOpt = userDetailsRepository.findByUsername(username);

        if (userOpt.isPresent()) {
            UserDetails user = userOpt.get();
            if (user.getSecurityQuestion().equals(securityQuestion) &&
                user.getAnswer().equals(answer)) {
                user.setPassword(passwordEncoder.encode(newPassword));  // hash new password
                userDetailsRepository.save(user);
                return "Password successfully reset for " + username + ".";
            }
            return "Invalid security question or answer.";
        }
        return "User not found.";
    }
    
}

