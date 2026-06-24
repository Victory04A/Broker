package com.brokerfinder.service;

import com.brokerfinder.model.ClientRequest;
import com.brokerfinder.repository.ClientRequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Service class for managing client service requests.
 * Handles submission and retrieval of client requests.
 * After submission, notifies matching brokers.
 */
@Service
public class ClientRequestService {

    private final ClientRequestRepository clientRequestRepository;
    private final BrokerNotificationService notificationService;

    public ClientRequestService(ClientRequestRepository clientRequestRepository,
                                BrokerNotificationService notificationService) {
        this.clientRequestRepository = clientRequestRepository;
        this.notificationService = notificationService;
    }

    /**
     * Submit a new client request with the current date.
     * Also notifies all APPROVED brokers that match the request's region and specialty.
     */
    public ClientRequest submitRequest(ClientRequest request) {
        request.setRequestDate(LocalDate.now());
        ClientRequest saved = clientRequestRepository.save(request);
        // Notify matching brokers about this request
        notificationService.notifyMatchingBrokers(saved);
        return saved;
    }

    /**
     * Retrieve all client requests.
     */
    public List<ClientRequest> getAllRequests() {
        return clientRequestRepository.findAll();
    }
}
