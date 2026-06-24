package com.brokerfinder.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity representing a notification sent to a broker about a client request.
 * Brokers can accept or reject requests assigned to them.
 */
@Entity
@Table(name = "broker_notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrokerNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    private ClientRequest request;

    @ManyToOne
    @JoinColumn(name = "broker_id", nullable = false)
    private Broker broker;

    /**
     * Status: PENDING, ACCEPTED, REJECTED
     */
    @Column(nullable = false)
    private String status = "PENDING";

    @Column(name = "notified_at", nullable = false)
    private LocalDateTime notifiedAt;
}
