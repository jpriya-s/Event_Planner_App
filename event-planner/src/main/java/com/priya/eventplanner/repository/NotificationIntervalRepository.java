package com.priya.eventplanner.repository;

import com.priya.eventplanner.model.NotificationInterval;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for managing NotificationInterval entities.
 */
public interface NotificationIntervalRepository extends JpaRepository<NotificationInterval, Integer> {

    /**
     * Find notification interval by description.
     *
     * @param intervalDesc interval description (e.g., "10 minutes before")
     * @return Optional containing the NotificationInterval if found
     */
    Optional<NotificationInterval> findByIntervalDesc(String intervalDesc);
    
}

