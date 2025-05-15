package me.diegxherrera.backcoachservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteSeatRequestDTO {

    @NotNull(message = "Seat ID must not be null")
    private UUID seatId;

    // Optional: audit or soft delete fields
    private String reason;
}