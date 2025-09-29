package com.priya.eventplanner.repository;

import com.priya.eventplanner.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing UserDetails entities.
 */
public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {

    /**
     * Finds a user by their username.
     *
     * @param username the username to search
     * @return Optional containing UserDetails if found
     */
    Optional<UserDetails> findByUsername(String username);
    
}

