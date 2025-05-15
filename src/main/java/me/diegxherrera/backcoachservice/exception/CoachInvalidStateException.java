package me.diegxherrera.backcoachservice.exception;

public class CoachInvalidStateException extends RuntimeException {
    public CoachInvalidStateException(String message) {
        super("Invalid state for Coach: " + message);
    }
}