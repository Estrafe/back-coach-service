package me.diegxherrera.backcoachservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import me.diegxherrera.backcoachservice.model.SeatClassEnum;
import me.diegxherrera.backcoachservice.model.SeatStatusEnum;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSeatRequestDTO {

    @NotNull(message = "Coach ID must not be null")
    private UUID coachId;

    @Min(value = 1, message = "Seat number must be at least 1")
    private int seatNumber;

    @NotNull(message = "Seat class must not be null")
    private SeatClassEnum seatClass;

    @NotNull(message = "Seat status must not be null")
    private SeatStatusEnum status;
}