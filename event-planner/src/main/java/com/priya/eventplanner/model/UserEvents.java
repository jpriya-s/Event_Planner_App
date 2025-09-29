package com.priya.eventplanner.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing the relationship between a user and an event.
 */
@Entity
@Table(
    name = "UserEvents",
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "event_id"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEvents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userEventId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserDetails user;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private EventDetails event;
    
}
