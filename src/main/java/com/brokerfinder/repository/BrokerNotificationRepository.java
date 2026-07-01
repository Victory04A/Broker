package com.brokerfinder.repository;

import com.brokerfinder.model.BrokerNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BrokerNotificationRepository extends JpaRepository<BrokerNotification, Long> {

    List<BrokerNotification> findByBrokerIdAndStatus(Long brokerId, String status);

    List<BrokerNotification> findByBrokerIdOrderByNotifiedAtDesc(Long brokerId);

    List<BrokerNotification> findByRequestId(Long requestId);

    void deleteByBrokerId(Long brokerId);
}
