package me.diegxherrera.backcoachservice.exception;

public class SeatAlreadyReservedException extends RuntimeException {
    public SeatAlreadyReservedException(int seatNumber) {
        super("Seat number " + seatNumber + " is already reserved.");
    }
}