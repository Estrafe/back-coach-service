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
public class SeatReleasedEventDTO {
    private UUID seatId;
    private UUID coachId;
    private int seatNumber;
    private String seatClass;
    private String status; // Available status
}