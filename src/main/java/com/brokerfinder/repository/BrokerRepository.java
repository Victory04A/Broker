package com.brokerfinder.repository;

import com.brokerfinder.model.Broker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Broker entity.
 * Provides CRUD operations and custom query methods.
 */
@Repository
public interface BrokerRepository extends JpaRepository<Broker, Long> {

    /**
     * Find all brokers with a given status (e.g., PENDING, APPROVED, REJECTED).
     */
    List<Broker> findByStatus(String status);

    /**
     * Search for APPROVED brokers by region and specialty.
     * Used by clients to find matching brokers.
     */
    List<Broker> findByStatusAndRegionAndSpecialty(String status, String region, String specialty);

    /**
     * Find a broker by email (used for duplicate check during registration).
     */
    boolean existsByEmail(String email);

    /**
     * Find a broker by email (used for broker login).
     */
    java.util.Optional<Broker> findByEmail(String email);
}
