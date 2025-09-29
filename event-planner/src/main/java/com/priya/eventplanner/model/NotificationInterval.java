package com.priya.eventplanner.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a notification interval (e.g., "30 minutes before").
 */
@Entity
@Table(name = "NotificationInterval")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationInterval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer intervalId;

    @Column(nullable = false, length = 100)
    private String intervalDesc;

    @Column(nullable = false)
    private Integer intervalMinutes;
    
}


