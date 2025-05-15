package me.diegxherrera.backcoachservice.controller;

import jakarta.validation.Valid;
import me.diegxherrera.backcoachservice.dto.request.CreateSeatRequestDTO;
import me.diegxherrera.backcoachservice.dto.request.DeleteSeatRequestDTO;
import me.diegxherrera.backcoachservice.dto.request.UpdateSeatRequestDTO;
import me.diegxherrera.backcoachservice.dto.response.SeatResponseDTO;
import me.diegxherrera.backcoachservice.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/seats")
public class SeatController {

    private final SeatService seatService;

    @Autowired
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    // Create a new seat
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SeatResponseDTO createSeat(@Valid @RequestBody CreateSeatRequestDTO request) {
        return seatService.createSeat(request);
    }

    // Get a seat by its ID
    @GetMapping("/{id}")
    public SeatResponseDTO getSeatById(@PathVariable UUID id) {
        return seatService.getSeatById(id);
    }

    @GetMapping("/available")
    public List<SeatResponseDTO> getAvailableSeats() {
        return seatService.getAvailableSeats();
    }

    // Get all seats for a specific coach
    @GetMapping("/coach/{coachId}")
    public List<SeatResponseDTO> getSeatsByCoachId(@PathVariable UUID coachId) {
        return seatService.getSeatsByCoachId(coachId);
    }

    // Get all seats
    @GetMapping
    public List<SeatResponseDTO> getAllSeats() {
        return seatService.getAllSeats();
    }

    // Update an existing seat
    @PutMapping("/{id}")
    public SeatResponseDTO updateSeat(@PathVariable UUID id, @Valid @RequestBody UpdateSeatRequestDTO request) {
        return seatService.updateSeat(id, request);
    }

    // Delete a seat
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSeat(@Valid @RequestParam DeleteSeatRequestDTO request) {
        seatService.deleteSeat(request);
    }
}