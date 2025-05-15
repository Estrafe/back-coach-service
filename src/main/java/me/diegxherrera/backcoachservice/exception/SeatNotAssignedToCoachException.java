package me.diegxherrera.backcoachservice.exception;

import java.util.UUID;

public class SeatNotAssignedToCoachException extends RuntimeException {
    public SeatNotAssignedToCoachException(UUID seatId) {
        super("Seat with ID " + seatId + " is not assigned to any Coach.");
    }
}