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
public class SeatUpdatedEventDTO {
    private UUID id;
    private int seatNumber;
    private UUID coachId;
    private String seatClass;
    private String status;
}