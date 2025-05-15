package me.diegxherrera.backcoachservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "coach")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoachEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private int coachNumber;

    @Column(name = "train_id", nullable = false)
    private UUID trainId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CoachTypeEnum coachType;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "coach_seat_ids", joinColumns = @JoinColumn(name = "coach_id"))
    @Column(name = "seat_id")
    private List<UUID> seatIds;
}