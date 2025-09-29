package com.priya.eventplanner.dto;

import lombok.*;

/**
 * Data Transfer Object for user details.
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailsDTO {
    private String username;
    private String password;          
    private String email;
    private String mobileNumber;
    private String firstName;
    private String lastName;
    private String securityQuestion;
    private String answer;
    private String notificationMethod; // e.g., SMS, EMAIL, BOTH, NONE
}
