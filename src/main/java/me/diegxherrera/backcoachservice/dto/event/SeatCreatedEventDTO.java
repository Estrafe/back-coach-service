package me.diegxherrera.backcoachservice.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatCreatedEventDTO {
    private UUID id;
    private int seatNumber;
    private UUID coachId;
    private String seatClass; // SeatClassEnum or String
    private String status; // SeatStatusEnum or String
}