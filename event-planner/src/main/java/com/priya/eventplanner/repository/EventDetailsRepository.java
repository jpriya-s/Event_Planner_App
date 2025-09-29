package com.priya.eventplanner.repository;

import com.priya.eventplanner.model.EventDetails;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for managing EventDetails entities.
 */
public interface EventDetailsRepository extends JpaRepository<EventDetails, Integer> {
    
}