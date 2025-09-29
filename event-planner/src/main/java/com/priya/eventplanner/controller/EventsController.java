package com.priya.eventplanner.controller;

import com.priya.eventplanner.dto.EventDTO;
import com.priya.eventplanner.service.EventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller to manage user events.
 *
 */
@RestController
@RequestMapping("/events")
public class EventsController {

    private final EventService eventService;

    public EventsController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Get all events for a given user.
     *
     * @param userId user ID (mandatory)
     * @param sortOrder   optional ("asc" or "desc"), default = "desc"
     * @return list of EventDTO
     */
    @GetMapping("/getAllEvents")
    public List<EventDTO> getAllEvents(
            @RequestParam Integer userId,
            @RequestParam(required = false, defaultValue = "desc") String sortOrder) {
        return eventService.getAllEvents(userId, sortOrder);
    }

    /**
     * Create a new event.
     *
     * @param dto event details
     * @return created event
     */
    @PostMapping("/create")
    public EventDTO createEvent(@RequestBody EventDTO dto) {
        return eventService.createEvent(dto);
    }

    /**
     * Update an existing event.
     *
     * @param eventId ID of the event to update
     * @param dto updated details
     * @return updated event or error message
     */
    @PutMapping("/update/{eventId}")
    public Object updateEvent(@PathVariable Integer eventId, @RequestBody EventDTO dto) {
        EventDTO updated = eventService.updateEvent(eventId, dto);
        if (updated != null) {
            return updated;
        }
        return "Event with ID " + eventId + " not found.";
    }

    /**
     * Delete an event.
     *
     * @param eventId ID of the event to delete
     * @return success or failure message
     */
    @DeleteMapping("/delete/{eventId}")
    public String deleteEvent(@PathVariable Integer eventId) {
        boolean deleted = eventService.deleteEvent(eventId);
        if (deleted) {
            return "Event with ID " + eventId + " deleted successfully.";
        }
        return "Event with ID " + eventId + " not found.";
    }

}

