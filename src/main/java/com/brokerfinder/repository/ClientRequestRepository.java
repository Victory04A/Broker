package com.brokerfinder.repository;

import com.brokerfinder.model.ClientRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientRequestRepository extends JpaRepository<ClientRequest, Long> {
}
