package me.diegxherrera.backcoachservice.repository;

import me.diegxherrera.backcoachservice.model.CoachEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CoachRepository extends JpaRepository<CoachEntity, UUID> {

    // Example: Find coaches by train ID
    List<CoachEntity> findByTrainId(UUID trainId);

    // Example: Find coaches by type (if applicable)
    List<CoachEntity> findByCoachType(String coachType);

    // Custom method to check if a coach with the same coachNumber exists
    boolean existsByCoachNumber(int coachNumber);
}