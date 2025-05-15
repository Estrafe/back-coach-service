package me.diegxherrera.backcoachservice.exception;

import me.diegxherrera.backcoachservice.exception.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global Exception Handler for all exceptions related to Coaches, Seats, and other resources.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles resource not found exceptions for Coach and Seat entities.
     */
    @ExceptionHandler({CoachNotFoundException.class, SeatNotFoundException.class})
    public ResponseEntity<String> handleResourceNotFoundException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles resource already exists exceptions for Coach entities (e.g., trying to create a Coach that already exists).
     */
    @ExceptionHandler(CoachAlreadyExistsException.class)
    public ResponseEntity<String> handleCoachAlreadyExistsException(CoachAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * Handles resource already reserved exceptions for Seat entities (e.g., trying to reserve a Seat that is already reserved).
     */
    @ExceptionHandler(SeatAlreadyReservedException.class)
    public ResponseEntity<String> handleSeatAlreadyReservedException(SeatAlreadyReservedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * Handles resource unavailable exceptions for Seat entities (e.g., trying to release an unavailable Seat).
     */
    @ExceptionHandler(SeatUnavailableException.class)
    public ResponseEntity<String> handleSeatUnavailableException(SeatUnavailableException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles invalid state exceptions for Coach and Seat entities.
     */
    @ExceptionHandler({CoachInvalidStateException.class, SeatInvalidStateException.class})
    public ResponseEntity<String> handleInvalidStateException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles illegal argument exceptions for bad input logic related to Coach or Seat.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    /**
     * Handles database constraint violations (e.g., duplicates) for Coach and Seat entities.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrity(DataIntegrityViolationException ex) {
        if (ex.getMessage() != null && ex.getMessage().contains("Duplicate entry")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Database constraint violation: " + ex.getMessage());
        }
        return ResponseEntity.badRequest().body("Database constraint violation: " + ex.getMessage());
    }

    /**
     * Handles validation errors (e.g., @NotNull, @NotBlank) for Coach and Seat entities.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Catch-all for unexpected runtime errors related to Coach and Seat.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal Server Error: " + ex.getMessage());
    }

    /**
     * Handles transaction-related errors (e.g., transaction failures during Coach or Seat operations).
     */
    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<String> handleTransactionException(TransactionSystemException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transaction error: " + ex.getRootCause().getMessage());
    }
}