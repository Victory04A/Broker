package com.brokerfinder.service;

import com.brokerfinder.model.Broker;
import com.brokerfinder.model.BrokerNotification;
import com.brokerfinder.model.ClientRequest;
import com.brokerfinder.repository.BrokerNotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Matching approved brokers to client requests.
 */
@Service
public class BrokerNotificationService {

    private final BrokerNotificationRepository notificationRepository;
    private final BrokerService brokerService;

    public BrokerNotificationService(BrokerNotificationRepository notificationRepository,
                                     BrokerService brokerService) {
        this.notificationRepository = notificationRepository;
        this.brokerService = brokerService;
    }

    /**
     * Notify all APPROVED brokers matching the request.
     */
    public void notifyMatchingBrokers(ClientRequest request) {
        List<Broker> brokers = brokerService.searchBroker(request.getRegion(), request.getSpecialty());
        for (Broker broker : brokers) {
            BrokerNotification notification = new BrokerNotification();
            notification.setRequest(request);
            notification.setBroker(broker);
            notification.setStatus("PENDING");
            notification.setNotifiedAt(LocalDateTime.now());
            notificationRepository.save(notification);
        }
    }

    /**
     * Get all notifications for a broker.
     */
    public List<BrokerNotification> getNotificationsForBroker(Long brokerId) {
        return notificationRepository.findByBrokerIdOrderByNotifiedAtDesc(brokerId);
    }

    public List<BrokerNotification> getPendingNotifications(Long brokerId) {
        return notificationRepository.findByBrokerIdAndStatus(brokerId, "PENDING");
    }

    /**
     * Accept a request notification.
     */
    public BrokerNotification acceptRequest(Long notificationId) {
        BrokerNotification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setStatus("ACCEPTED");
        return notificationRepository.save(notification);
    }

    /**
     * Reject a request notification.
     */
    public BrokerNotification rejectRequest(Long notificationId) {
        BrokerNotification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setStatus("REJECTED");
        return notificationRepository.save(notification);
    }
}
