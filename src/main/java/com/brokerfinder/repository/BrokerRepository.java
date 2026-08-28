package com.brokerfinder.repository;

import com.brokerfinder.model.Broker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrokerRepository extends JpaRepository<Broker, Long> {


    List<Broker> findByStatus(String status);

    /**
     * Used by clients to find matching brokers.
     */
    List<Broker> findByStatusAndRegionAndSpecialty(String status, String region, String specialty);

    boolean existsByEmail(String email);

    /**
     * Find a broker by email (used for broker login).
     */
    java.util.Optional<Broker> findByEmail(String email);
}
