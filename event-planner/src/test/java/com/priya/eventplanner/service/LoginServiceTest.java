package com.priya.eventplanner.service;

import com.priya.eventplanner.dto.UserDetailsDTO;
import com.priya.eventplanner.model.UserDetails;
import com.priya.eventplanner.repository.UserDetailsRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class LoginServiceTest {

    @Mock
    private UserDetailsRepository userDetailsRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogin_Success() {
        String rawPassword = "pass1";
        String encodedPassword = "encodedPass1";
        UserDetails user = UserDetails.builder().username("user1").password(encodedPassword).build();
        when(userDetailsRepository.findByUsername("user1")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);
        String result = loginService.login("user1", rawPassword);
        assertEquals("Login successful for user1.", result);
    }

    @Test
    public void testLogin_Failure_WrongPassword() {
        String rawPassword = "wrongpass";
        String encodedPassword = "encodedPass1";
        UserDetails user = UserDetails.builder().username("user1").password(encodedPassword).build();
        when(userDetailsRepository.findByUsername("user1")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);
        String result = loginService.login("user1", rawPassword);
        assertEquals("Invalid password.", result);
    }

    @Test
    public void testLogin_Failure_UserNotFound() {
        when(userDetailsRepository.findByUsername("user1")).thenReturn(Optional.empty());
        String result = loginService.login("user1", "pass1");
        assertEquals("User not found.", result);
    }

    @Test
    public void testSignup_Success() {
        when(userDetailsRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPass1");
        when(userDetailsRepository.save(any(UserDetails.class))).thenReturn(UserDetails.builder().username("user1").build());
        UserDetailsDTO dto = UserDetailsDTO.builder()
                .username("user1")
                .password("pass1")
                .email("email@example.com")
                .mobileNumber("1234567890")
                .firstName("First")
                .lastName("Last")
                .securityQuestion("Q?")
                .answer("A")
                .notificationMethod("Email")
                .build();
        String result = loginService.signup(dto);
        assertEquals("Signup successful for user1.", result);
    }

    @Test
    public void testSignup_UsernameExists() {
        UserDetails user = UserDetails.builder().username("user1").build();
        when(userDetailsRepository.findByUsername("user1")).thenReturn(Optional.of(user));
        UserDetailsDTO dto = UserDetailsDTO.builder().username("user1").build();
        String result = loginService.signup(dto);
        assertEquals("Username already exists.", result);
    }

    @Test
    public void testResetPassword_Success() {
        UserDetails user = UserDetails.builder()
                .username("user1")
                .securityQuestion("Q?")
                .answer("A")
                .password("oldEncodedPass")
                .build();
        when(userDetailsRepository.findByUsername("user1")).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("newpass")).thenReturn("newEncodedPass");
        when(userDetailsRepository.save(any(UserDetails.class))).thenReturn(user);
        String result = loginService.resetPassword("user1", "Q?", "A", "newpass");
        assertEquals("Password successfully reset for user1.", result);
        assertEquals("newEncodedPass", user.getPassword());
    }

    @Test
    public void testResetPassword_InvalidSecurity() {
        UserDetails user = UserDetails.builder()
                .username("user1")
                .securityQuestion("Q?")
                .answer("A")
                .password("oldEncodedPass")
                .build();
        when(userDetailsRepository.findByUsername("user1")).thenReturn(Optional.of(user));
        String result = loginService.resetPassword("user1", "WrongQ", "WrongA", "newpass");
        assertEquals("Invalid security question or answer.", result);
    }

    @Test
    public void testResetPassword_UserNotFound() {
        when(userDetailsRepository.findByUsername("user1")).thenReturn(Optional.empty());
        String result = loginService.resetPassword("user1", "Q?", "A", "newpass");
        assertEquals("User not found.", result);
    }
}
