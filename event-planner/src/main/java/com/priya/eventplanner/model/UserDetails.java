package com.priya.eventplanner.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Entity representing a event user.
 */
@Entity
@Table(name = "UserDetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    private String mobileNumber;
    private String firstName;
    private String lastName;
    private String securityQuestion;
    private String answer;

    @ManyToOne
    @JoinColumn(name = "method_id", nullable = false)
    private NotificationMethod notificationMethod;

    @OneToMany(mappedBy = "user")
    private List<UserEvents> userEvents;
    
}
