package com.priya.eventplanner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for event details.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDTO {

    private Integer eventId;
    private String eventName;
    private String eventDescription;
    private String startTime;
    private String endTime;
    private String notificationInterval;
    
}
