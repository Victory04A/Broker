package com.brokerfinder.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Service request submitted by a client.
 */
@Entity
@Table(name = "client_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Client name is required")
    @Column(name = "client_name", nullable = false)
    private String clientName;

    @NotBlank(message = "Phone number is required")
    @Column(nullable = false)
    private String phone;

    @NotBlank(message = "Region is required")
    @Column(nullable = false)
    private String region;

    @NotBlank(message = "Specialty is required")
    @Column(nullable = false)
    private String specialty;

    @Positive(message = "Budget must be a positive number")
    @Column(nullable = false)
    private Double budget;

    @Column(name = "request_date", nullable = false)
    private LocalDate requestDate;
}
