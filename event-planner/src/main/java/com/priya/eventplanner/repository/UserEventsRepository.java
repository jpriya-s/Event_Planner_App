package com.priya.eventplanner.repository;

import com.priya.eventplanner.model.UserEvents;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for managing UserEvents entities.
 */
public interface UserEventsRepository extends JpaRepository<UserEvents, Integer> {
    
    List<UserEvents> findByUserUserId(Integer userId);
    List<UserEvents> findByEventEventId(Integer eventId);
}



