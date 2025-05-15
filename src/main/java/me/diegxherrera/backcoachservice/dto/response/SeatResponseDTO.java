package me.diegxherrera.backcoachservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.diegxherrera.backcoachservice.model.SeatClassEnum;
import me.diegxherrera.backcoachservice.model.SeatStatusEnum;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatResponseDTO {

    private UUID id;
    private int seatNumber;
    private SeatClassEnum seatClass;
    private SeatStatusEnum status;
    private UUID coachId;
}