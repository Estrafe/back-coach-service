package me.diegxherrera.backcoachservice.exception;

import java.util.UUID;

public class CoachNotAssignedToTrainException extends RuntimeException {
    public CoachNotAssignedToTrainException(UUID coachId) {
        super("Coach with ID " + coachId + " is not assigned to any Train.");
    }
}