package com.priya.eventplanner.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a notification method (SMS, EMAIL, BOTH, NONE).
 */
@Entity
@Table(name = "NotificationMethod")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer methodId;

    @Column(nullable = false, unique = true, length = 20)
    private String methodName;
    
}

