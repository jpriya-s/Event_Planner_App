package com.priya.eventplanner.service;

import com.priya.eventplanner.dto.EventDTO;
import com.priya.eventplanner.model.EventDetails;
import com.priya.eventplanner.model.NotificationInterval;
import com.priya.eventplanner.model.UserEvents;
import com.priya.eventplanner.repository.EventDetailsRepository;
import com.priya.eventplanner.repository.NotificationIntervalRepository;
import com.priya.eventplanner.repository.UserEventsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for event CRUD operations using EventDTO.
 */
@Service
public class EventService {

    private final EventDetailsRepository eventDetailsRepository;
    private final NotificationIntervalRepository notificationIntervalRepository;
    private final UserEventsRepository userEventsRepository;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public EventService(EventDetailsRepository eventDetailsRepository,
                        NotificationIntervalRepository notificationIntervalRepository,
                        UserEventsRepository userEventsRepository) {
        this.eventDetailsRepository = eventDetailsRepository;
        this.notificationIntervalRepository = notificationIntervalRepository;
        this.userEventsRepository = userEventsRepository;
    }

    /**
     * Retrieves all events for a user with sorting options.
     *
     * @param userId user identifier
     * @param sort   sorting order ("asc" or "desc"), defaults to "desc"
     * @return list of EventDTO representing the user's events
     */
    public List<EventDTO> getAllEvents(Integer userId, String sort) {
        List<UserEvents> userEvents = userEventsRepository.findByUserUserId(userId);

        // Convert UserEvents to EventDTO
        List<EventDTO> events = userEvents.stream()
                .map(ue -> new EventDTO(
                        ue.getEvent().getEventId(),
                        ue.getEvent().getEventName(),
                        ue.getEvent().getEventDescription(),
                        ue.getEvent().getStartTime().format(FORMATTER),
                        ue.getEvent().getEndTime().format(FORMATTER),
                        ue.getEvent().getNotificationInterval().getIntervalDesc()
                ))
                .collect(Collectors.toList());

        // Apply sorting on LocalDateTime
        Comparator<EventDTO> comparator = Comparator.comparing(
                e -> LocalDateTime.parse(e.getStartTime(), FORMATTER)
        );

        if ("asc".equalsIgnoreCase(sort)) {
            events.sort(comparator);
        } else {
            events.sort(comparator.reversed()); // default: latest first
        }

        return events;
    }

    /**
     * Create a new event.
     */
    public EventDTO createEvent(EventDTO dto) {
        EventDetails entity = mapToEntity(dto);
        EventDetails saved = eventDetailsRepository.save(entity);
        return mapToDTO(saved);
    }

    /**
     * Update an existing event.
     */
    public EventDTO updateEvent(Integer eventId, EventDTO dto) {
        Optional<EventDetails> existingOpt = eventDetailsRepository.findById(eventId);

        if (existingOpt.isPresent()) {
            EventDetails existing = existingOpt.get();
            existing.setEventName(dto.getEventName());
            existing.setEventDescription(dto.getEventDescription());
            existing.setStartTime(LocalDateTime.parse(dto.getStartTime(), FORMATTER));
            existing.setEndTime(LocalDateTime.parse(dto.getEndTime(), FORMATTER));

            NotificationInterval interval = notificationIntervalRepository
                    .findByIntervalDesc(dto.getNotificationInterval())
                    .orElseThrow(() -> new RuntimeException("Invalid notification interval: " + dto.getNotificationInterval()));

            existing.setNotificationInterval(interval);

            EventDetails updated = eventDetailsRepository.save(existing);
            return mapToDTO(updated);
        }
        return null;
    }

    /**
     * Delete an event (and its user-event mappings).
     *
     * @param eventId event ID
     * @return true if deleted, false if not found
     */
    public boolean deleteEvent(Integer eventId) {
        if (eventDetailsRepository.existsById(eventId)) {
            // First delete user-event mappings
            List<UserEvents> mappings = userEventsRepository.findByEventEventId(eventId);
            if (!mappings.isEmpty()) {
                userEventsRepository.deleteAll(mappings);
            }

            // Now delete the event itself
            eventDetailsRepository.deleteById(eventId);
            return true;
        }
        return false;
    }

    /**
     * Convert DTO to Entity.
     */
    private EventDetails mapToEntity(EventDTO dto) {
        NotificationInterval interval = notificationIntervalRepository
                .findByIntervalDesc(dto.getNotificationInterval())
                .orElseThrow(() -> new RuntimeException("Invalid notification interval: " + dto.getNotificationInterval()));

        return EventDetails.builder()
                .eventId(dto.getEventId())
                .eventName(dto.getEventName())
                .eventDescription(dto.getEventDescription())
                .startTime(LocalDateTime.parse(dto.getStartTime(), FORMATTER))
                .endTime(LocalDateTime.parse(dto.getEndTime(), FORMATTER))
                .notificationInterval(interval)
                .build();
    }

    /**
     * Convert Entity to DTO.
     */
    private EventDTO mapToDTO(EventDetails entity) {
        return EventDTO.builder()
                .eventId(entity.getEventId())
                .eventName(entity.getEventName())
                .eventDescription(entity.getEventDescription())
                .startTime(entity.getStartTime().format(FORMATTER))
                .endTime(entity.getEndTime().format(FORMATTER))
                .notificationInterval(entity.getNotificationInterval() != null
                        ? entity.getNotificationInterval().getIntervalDesc()
                        : null)
                .build();
    }
    
}
