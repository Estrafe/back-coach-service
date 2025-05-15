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
public class CoachCreatedEventDTO {
    private UUID id;
    private int coachNumber;
    private UUID trainId;
    private String coachType;
}