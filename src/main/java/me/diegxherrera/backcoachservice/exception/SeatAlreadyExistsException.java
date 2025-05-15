package me.diegxherrera.backcoachservice.exception;

public class SeatAlreadyExistsException extends RuntimeException {
    public SeatAlreadyExistsException(int seatNumber) {
        super("Seat with number " + seatNumber + " already exists");
    }
}