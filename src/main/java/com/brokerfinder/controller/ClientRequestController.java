package com.brokerfinder.controller;

import com.brokerfinder.model.ClientRequest;
import com.brokerfinder.service.ClientRequestService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * submitting and viewing client requests.
 */
@RestController
@RequestMapping("/api/requests")
public class ClientRequestController {

    private final ClientRequestService clientRequestService;

    public ClientRequestController(ClientRequestService clientRequestService) {
        this.clientRequestService = clientRequestService;
    }


    @PostMapping
    public ResponseEntity<?> submitRequest(@Valid @RequestBody ClientRequest request) {
        try {
            ClientRequest saved = clientRequestService.submitRequest(request);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<ClientRequest> getAllRequests() {
        return clientRequestService.getAllRequests();
    }
}
