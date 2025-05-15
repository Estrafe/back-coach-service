package me.diegxherrera.backcoachservice.exception;

import java.util.UUID;

public class SeatNotFoundException extends RuntimeException {
    public SeatNotFoundException(UUID seatId) {
        super("Seat not found with ID: " + seatId);
    }
}