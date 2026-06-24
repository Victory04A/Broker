package com.brokerfinder.controller;

import com.brokerfinder.model.Broker;
import com.brokerfinder.service.BrokerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for broker management.
 * Provides endpoints for registration and admin management.
 */
@RestController
@RequestMapping("/api/brokers")
public class BrokerController {

    private final BrokerService brokerService;

    public BrokerController(BrokerService brokerService) {
        this.brokerService = brokerService;
    }

    /**
     * Register a new broker.
     * POST /api/brokers/register
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerBroker(@Valid @RequestBody Broker broker) {
        try {
            Broker saved = brokerService.registerBroker(broker);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Get all brokers.
     * GET /api/brokers
     */
    @GetMapping
    public List<Broker> getAllBrokers() {
        return brokerService.getAllBrokers();
    }

    /**
     * Approve a broker by ID.
     * PUT /api/brokers/{id}/approve
     */
    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approveBroker(@PathVariable Long id) {
        try {
            Broker approved = brokerService.approveBroker(id);
            return ResponseEntity.ok(approved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Reject a broker by ID.
     * PUT /api/brokers/{id}/reject
     */
    @PutMapping("/{id}/reject")
    public ResponseEntity<?> rejectBroker(@PathVariable Long id) {
        try {
            Broker rejected = brokerService.rejectBroker(id);
            return ResponseEntity.ok(rejected);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Search for approved brokers by region and specialty.
     * GET /api/brokers/search?region=...&specialty=...
     */
    @GetMapping("/search")
    public List<Broker> searchBrokers(
            @RequestParam String region,
            @RequestParam String specialty) {
        return brokerService.searchBroker(region, specialty);
    }
}
