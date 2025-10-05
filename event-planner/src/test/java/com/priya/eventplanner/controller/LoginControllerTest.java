package com.priya.eventplanner.controller;

import com.priya.eventplanner.dto.UserDetailsDTO;
import com.priya.eventplanner.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class LoginControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LoginService loginService;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    public void testLogin_Success() throws Exception {
        when(loginService.login(anyString(), anyString())).thenReturn("Login successful");
        mockMvc.perform(post("/auth/login")
                .param("username", "user1")
                .param("password", "pass1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Login successful"));
    }

    @Test
    public void testLogin_Failure() throws Exception {
        when(loginService.login(anyString(), anyString())).thenReturn("Invalid credentials");
        mockMvc.perform(post("/auth/login")
                .param("username", "user1")
                .param("password", "wrongpass"))
                .andExpect(status().isOk())
                .andExpect(content().string("Invalid credentials"));
    }

    @Test
    public void testSignup_Success() throws Exception {
        when(loginService.signup(any(UserDetailsDTO.class))).thenReturn("Signup successful");
        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Signup successful"));
    }

    @Test
    public void testSignup_Failure() throws Exception {
        when(loginService.signup(any(UserDetailsDTO.class))).thenReturn("Signup failed");
        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Signup failed"));
    }

    @Test
    public void testResetPassword_Success() throws Exception {
        when(loginService.resetPassword(anyString(), anyString(), anyString(), anyString())).thenReturn("Password reset successful");
        mockMvc.perform(post("/auth/reset-password")
                .param("username", "user1")
                .param("securityQuestion", "question")
                .param("answer", "answer")
                .param("newPassword", "newpass"))
                .andExpect(status().isOk())
                .andExpect(content().string("Password reset successful"));
    }

    @Test
    public void testResetPassword_Failure() throws Exception {
        when(loginService.resetPassword(anyString(), anyString(), anyString(), anyString())).thenReturn("Password reset failed");
        mockMvc.perform(post("/auth/reset-password")
                .param("username", "user1")
                .param("securityQuestion", "question")
                .param("answer", "wronganswer")
                .param("newPassword", "newpass"))
                .andExpect(status().isOk())
                .andExpect(content().string("Password reset failed"));
    }
}
