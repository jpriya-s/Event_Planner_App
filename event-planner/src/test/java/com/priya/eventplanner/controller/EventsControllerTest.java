package com.priya.eventplanner.controller;

import com.priya.eventplanner.dto.EventDTO;
import com.priya.eventplanner.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EventsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventsController eventsController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(eventsController).build();
    }

    @Test
    public void testGetAllEvents() throws Exception {
        EventDTO event1 = new EventDTO();
        EventDTO event2 = new EventDTO();
        List<EventDTO> events = Arrays.asList(event1, event2);
        when(eventService.getAllEvents(anyInt(), any())).thenReturn(events);

        mockMvc.perform(get("/events/getAllEvents")
                .param("userId", "1")
                .param("sortOrder", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());
    }

    @Test
    public void testCreateEvent() throws Exception {
        EventDTO event = new EventDTO();
        when(eventService.createEvent(any(EventDTO.class))).thenReturn(event);

        mockMvc.perform(post("/events/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateEvent_Found() throws Exception {
        EventDTO event = new EventDTO();
        when(eventService.updateEvent(eq(1), any(EventDTO.class))).thenReturn(event);

        mockMvc.perform(put("/events/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateEvent_NotFound() throws Exception {
        when(eventService.updateEvent(eq(1), any(EventDTO.class))).thenReturn(null);

        mockMvc.perform(put("/events/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Event with ID 1 not found."));
    }

    @Test
    public void testDeleteEvent_Success() throws Exception {
        when(eventService.deleteEvent(1)).thenReturn(true);

        mockMvc.perform(delete("/events/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Event with ID 1 deleted successfully."));
    }

    @Test
    public void testDeleteEvent_NotFound() throws Exception {
        when(eventService.deleteEvent(1)).thenReturn(false);

        mockMvc.perform(delete("/events/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Event with ID 1 not found."));
    }
}
