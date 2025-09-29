package com.priya.eventplanner.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity representing an event with details.
 */
@Entity
@Table(name = "EventDetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventId;

    @Column(nullable = false, length = 100)
    private String eventName;

    private String eventDescription;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "interval_id", nullable = false)
    private NotificationInterval notificationInterval;

    @OneToMany(mappedBy = "event")
    private List<UserEvents> userEvents;
    
}
