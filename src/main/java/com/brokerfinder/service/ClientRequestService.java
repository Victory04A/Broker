package com.brokerfinder.service;

import com.brokerfinder.model.ClientRequest;
import com.brokerfinder.repository.ClientRequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
     * Submit a new client request.
     */
    public ClientRequest submitRequest(ClientRequest request) {
        request.setRequestDate(LocalDate.now());
        ClientRequest saved = clientRequestRepository.save(request);

        notificationService.notifyMatchingBrokers(saved);
        return saved;
    }

    
    public List<ClientRequest> getAllRequests() {
        return clientRequestRepository.findAll();
    }
}
