package com.brokerfinder.repository;

import com.brokerfinder.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Admin entity.
 * Provides CRUD operations and admin authentication methods.
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    /**
     * Find an admin by username (used for login verification).
     */
    Optional<Admin> findByUsername(String username);
}
