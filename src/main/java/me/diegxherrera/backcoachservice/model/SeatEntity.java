package me.diegxherrera.backcoachservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "seat")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private int seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatClassEnum seatClass;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatusEnum status;

    @Column(name = "coach_id", nullable = false, insertable = false, updatable = false)
    private UUID coachId;

    public boolean isAvailable() {
        return status == SeatStatusEnum.AVAILABLE;
    }

    public void reserve() {
        if (!isAvailable()) throw new IllegalStateException("Seat is not available");
        this.status = SeatStatusEnum.RESERVED;
    }

    public void release() {
        if (status != SeatStatusEnum.RESERVED) throw new IllegalStateException("Cannot release a non-reserved seat");
        this.status = SeatStatusEnum.AVAILABLE;
    }
}