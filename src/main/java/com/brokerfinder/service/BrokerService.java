package com.brokerfinder.service;

import com.brokerfinder.model.Broker;
import com.brokerfinder.repository.BrokerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing broker operations.
 * Handles registration, approval, rejection, and search functionality.
 */
@Service
public class BrokerService {

    private final BrokerRepository brokerRepository;

    public BrokerService(BrokerRepository brokerRepository) {
        this.brokerRepository = brokerRepository;
    }

    /**
     * Register a new broker with PENDING status.
     * Checks for duplicate email before saving.
     */
    public Broker registerBroker(Broker broker) {
        if (brokerRepository.existsByEmail(broker.getEmail())) {
            throw new RuntimeException("A broker with this email already exists.");
        }
        broker.setStatus("PENDING");
        return brokerRepository.save(broker);
    }

    /**
     * Retrieve all registered brokers.
     */
    public List<Broker> getAllBrokers() {
        return brokerRepository.findAll();
    }

    /**
     * Approve a broker by setting their status to APPROVED.
     */
    public Broker approveBroker(Long id) {
        Broker broker = brokerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Broker not found with id: " + id));
        broker.setStatus("APPROVED");
        return brokerRepository.save(broker);
    }

    /**
     * Reject a broker by setting their status to REJECTED.
     */
    public Broker rejectBroker(Long id) {
        Broker broker = brokerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Broker not found with id: " + id));
        broker.setStatus("REJECTED");
        return brokerRepository.save(broker);
    }

    /**
     * Get a single broker by ID.
     */
    public Broker getBrokerById(Long id) {
        return brokerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Broker not found with id: " + id));
    }

    /**
     * Search for APPROVED brokers by region and specialty.
     * Used by clients looking for brokers.
     */
    public List<Broker> searchBroker(String region, String specialty) {
        return brokerRepository.findByStatusAndRegionAndSpecialty("APPROVED", region, specialty);
    }

    /**
     * Get all brokers with a specific status.
     */
    public List<Broker> getBrokersByStatus(String status) {
        return brokerRepository.findByStatus(status);
    }

    /**
     * Find a broker by email (used for login).
     */
    public Optional<Broker> findByEmail(String email) {
        return brokerRepository.findByEmail(email);
    }
}
