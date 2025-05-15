package me.diegxherrera.backcoachservice.repository;

import me.diegxherrera.backcoachservice.model.SeatEntity;
import me.diegxherrera.backcoachservice.model.SeatStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SeatRepository extends JpaRepository<SeatEntity, UUID> {

    // Example: Find seats by coach ID
    List<SeatEntity> findByCoachId(UUID coachId);

    // Example: Find available seats (if there's a field for availability)
    List<SeatEntity> findByStatus(SeatStatusEnum status);

    boolean existsBySeatNumber(int seatNumber);
}