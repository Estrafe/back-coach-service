package me.diegxherrera.backcoachservice.exception;

public class SeatInvalidStateException extends RuntimeException {
    public SeatInvalidStateException(String message) {
        super("Invalid state for Seat: " + message);
    }
}