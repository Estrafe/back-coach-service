package me.diegxherrera.backcoachservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import me.diegxherrera.backcoachservice.model.CoachTypeEnum;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCoachRequestDTO {

    @Min(value = 1, message = "Coach number must be at least 1")
    private int coachNumber;

    @NotNull(message = "Coach type must not be null")
    private CoachTypeEnum coachType;
}