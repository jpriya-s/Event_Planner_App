package com.priya.eventplanner.service;

import com.priya.eventplanner.dto.EventDTO;
import com.priya.eventplanner.model.EventDetails;
import com.priya.eventplanner.model.NotificationInterval;
import com.priya.eventplanner.model.UserEvents;
import com.priya.eventplanner.repository.EventDetailsRepository;
import com.priya.eventplanner.repository.NotificationIntervalRepository;
import com.priya.eventplanner.repository.UserEventsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class EventServiceTest {

    @Mock
    private EventDetailsRepository eventDetailsRepository;
    @Mock
    private NotificationIntervalRepository notificationIntervalRepository;
    @Mock
    private UserEventsRepository userEventsRepository;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllEvents_Asc() {
        UserEvents ue = mock(UserEvents.class);
        EventDetails ed = mock(EventDetails.class);
        NotificationInterval ni = mock(NotificationInterval.class);
        when(ue.getEvent()).thenReturn(ed);
        when(ed.getEventId()).thenReturn(1);
        when(ed.getEventName()).thenReturn("Event");
        when(ed.getEventDescription()).thenReturn("Desc");
        when(ed.getStartTime()).thenReturn(LocalDateTime.of(2025, 10, 4, 10, 0));
        when(ed.getEndTime()).thenReturn(LocalDateTime.of(2025, 10, 4, 12, 0));
        when(ed.getNotificationInterval()).thenReturn(ni);
        when(ni.getIntervalDesc()).thenReturn("Daily");
        when(userEventsRepository.findByUserUserId(anyInt())).thenReturn(Collections.singletonList(ue));

        List<EventDTO> result = eventService.getAllEvents(1, "asc");
        assertEquals(1, result.size());
        assertEquals("Event", result.get(0).getEventName());
    }

    @Test
    public void testCreateEvent() {
        NotificationInterval ni = mock(NotificationInterval.class);
        when(notificationIntervalRepository.findByIntervalDesc(anyString())).thenReturn(Optional.of(ni));
        EventDetails ed = mock(EventDetails.class);
        when(eventDetailsRepository.save(any(EventDetails.class))).thenReturn(ed);
        when(ed.getEventId()).thenReturn(1);
        when(ed.getEventName()).thenReturn("Event");
        when(ed.getEventDescription()).thenReturn("Desc");
        when(ed.getStartTime()).thenReturn(LocalDateTime.of(2025, 10, 4, 10, 0));
        when(ed.getEndTime()).thenReturn(LocalDateTime.of(2025, 10, 4, 12, 0));
        when(ed.getNotificationInterval()).thenReturn(ni);
        when(ni.getIntervalDesc()).thenReturn("Daily");

        EventDTO dto = EventDTO.builder()
                .eventId(1)
                .eventName("Event")
                .eventDescription("Desc")
                .startTime("2025-10-04T10:00:00")
                .endTime("2025-10-04T12:00:00")
                .notificationInterval("Daily")
                .build();

        EventDTO result = eventService.createEvent(dto);
        assertEquals("Event", result.getEventName());
    }

    @Test
    public void testUpdateEvent_Found() {
        NotificationInterval ni = mock(NotificationInterval.class);
        EventDetails ed = mock(EventDetails.class);
        when(eventDetailsRepository.findById(anyInt())).thenReturn(Optional.of(ed));
        when(notificationIntervalRepository.findByIntervalDesc(anyString())).thenReturn(Optional.of(ni));
        when(eventDetailsRepository.save(any(EventDetails.class))).thenReturn(ed);
        when(ed.getEventId()).thenReturn(1);
        when(ed.getEventName()).thenReturn("Event");
        when(ed.getEventDescription()).thenReturn("Desc");
        when(ed.getStartTime()).thenReturn(LocalDateTime.of(2025, 10, 4, 10, 0));
        when(ed.getEndTime()).thenReturn(LocalDateTime.of(2025, 10, 4, 12, 0));
        when(ed.getNotificationInterval()).thenReturn(ni);
        when(ni.getIntervalDesc()).thenReturn("Daily");

        EventDTO dto = EventDTO.builder()
                .eventId(1)
                .eventName("Event")
                .eventDescription("Desc")
                .startTime("2025-10-04T10:00:00")
                .endTime("2025-10-04T12:00:00")
                .notificationInterval("Daily")
                .build();

        EventDTO result = eventService.updateEvent(1, dto);
        assertNotNull(result);
        assertEquals("Event", result.getEventName());
    }

    @Test
    public void testUpdateEvent_NotFound() {
        when(eventDetailsRepository.findById(anyInt())).thenReturn(Optional.empty());
        EventDTO dto = EventDTO.builder().build();
        EventDTO result = eventService.updateEvent(1, dto);
        assertNull(result);
    }

    @Test
    public void testDeleteEvent_Success() {
        when(eventDetailsRepository.existsById(anyInt())).thenReturn(true);
        when(userEventsRepository.findByEventEventId(anyInt())).thenReturn(Collections.emptyList());
        doNothing().when(eventDetailsRepository).deleteById(anyInt());
        boolean result = eventService.deleteEvent(1);
        assertTrue(result);
    }

    @Test
    public void testDeleteEvent_WithMappings() {
        when(eventDetailsRepository.existsById(anyInt())).thenReturn(true);
        UserEvents ue = mock(UserEvents.class);
        when(userEventsRepository.findByEventEventId(anyInt())).thenReturn(Collections.singletonList(ue));
        doNothing().when(userEventsRepository).deleteAll(anyList());
        doNothing().when(eventDetailsRepository).deleteById(anyInt());
        boolean result = eventService.deleteEvent(1);
        assertTrue(result);
    }

    @Test
    public void testDeleteEvent_NotFound() {
        when(eventDetailsRepository.existsById(anyInt())).thenReturn(false);
        boolean result = eventService.deleteEvent(1);
        assertFalse(result);
    }
}
