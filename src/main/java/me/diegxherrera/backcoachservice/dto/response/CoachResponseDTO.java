package me.diegxherrera.backcoachservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.diegxherrera.backcoachservice.model.CoachTypeEnum;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoachResponseDTO {

    private UUID id;
    private int coachNumber;
    private UUID trainId;
    private CoachTypeEnum coachType;
    private List<UUID> seatIds;
}