package me.diegxherrera.backcoachservice.exception;

public class SeatUnavailableException extends RuntimeException {
    public SeatUnavailableException(int seatNumber) {
        super("Seat number " + seatNumber + " is unavailable for reservation.");
    }
}