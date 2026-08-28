package com.brokerfinder.controller;

import com.brokerfinder.model.Broker;
import com.brokerfinder.service.BrokerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/brokers")
public class BrokerController {

    private final BrokerService brokerService;

    public BrokerController(BrokerService brokerService) {
        this.brokerService = brokerService;
    }

    /**
     * Register a new broker.
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

    
    @GetMapping
    public List<Broker> getAllBrokers() {
        return brokerService.getAllBrokers();
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approveBroker(@PathVariable Long id) {
        try {
            Broker approved = brokerService.approveBroker(id);
            return ResponseEntity.ok(approved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<?> rejectBroker(@PathVariable Long id) {
        try {
            Broker rejected = brokerService.rejectBroker(id);
            return ResponseEntity.ok(rejected);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public List<Broker> searchBrokers(
            @RequestParam String region,
            @RequestParam String specialty) {
        return brokerService.searchBroker(region, specialty);
    }
}
