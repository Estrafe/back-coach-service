package me.diegxherrera.backcoachservice.service;

import me.diegxherrera.backcoachservice.dto.request.CreateSeatRequestDTO;
import me.diegxherrera.backcoachservice.dto.request.UpdateSeatRequestDTO;
import me.diegxherrera.backcoachservice.dto.request.DeleteSeatRequestDTO;
import me.diegxherrera.backcoachservice.dto.response.SeatResponseDTO;

import java.util.List;
import java.util.UUID;

public interface SeatService {

    SeatResponseDTO createSeat(CreateSeatRequestDTO request);

    SeatResponseDTO getSeatById(UUID seatId);

    List<SeatResponseDTO> getAllSeats();

    List<SeatResponseDTO> getAvailableSeats();

    List<SeatResponseDTO> getSeatsByCoachId(UUID coachId);

    SeatResponseDTO updateSeat(UUID seatId, UpdateSeatRequestDTO request);

    void deleteSeat(DeleteSeatRequestDTO request);
}