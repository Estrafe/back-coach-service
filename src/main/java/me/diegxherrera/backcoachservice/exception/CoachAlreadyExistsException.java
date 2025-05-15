package me.diegxherrera.backcoachservice.exception;

public class CoachAlreadyExistsException extends RuntimeException {

    public CoachAlreadyExistsException(String message) {
        super(message);
    }
}