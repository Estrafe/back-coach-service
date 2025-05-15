package me.diegxherrera.backcoachservice.service.impl;

import me.diegxherrera.backcoachservice.dto.event.SeatCreatedEventDTO;
import me.diegxherrera.backcoachservice.dto.event.SeatUpdatedEventDTO;
import me.diegxherrera.backcoachservice.dto.event.SeatDeletedEventDTO;
import me.diegxherrera.backcoachservice.dto.request.CreateSeatRequestDTO;
import me.diegxherrera.backcoachservice.dto.request.DeleteSeatRequestDTO;
import me.diegxherrera.backcoachservice.dto.request.UpdateSeatRequestDTO;
import me.diegxherrera.backcoachservice.dto.response.SeatResponseDTO;
import me.diegxherrera.backcoachservice.event.EventPublisher;
import me.diegxherrera.backcoachservice.mapper.SeatMapper;
import me.diegxherrera.backcoachservice.model.SeatEntity;
import me.diegxherrera.backcoachservice.model.SeatStatusEnum;
import me.diegxherrera.backcoachservice.repository.SeatRepository;
import me.diegxherrera.backcoachservice.service.SeatService;
import me.diegxherrera.backcoachservice.exception.SeatNotFoundException;
import me.diegxherrera.backcoachservice.exception.SeatAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;
    private final EventPublisher eventPublisher;

    @Autowired
    public SeatServiceImpl(SeatRepository seatRepository, SeatMapper seatMapper, EventPublisher eventPublisher) {
        this.seatRepository = seatRepository;
        this.seatMapper = seatMapper;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public List<SeatResponseDTO> getAvailableSeats() {
        return seatRepository.findByStatus(SeatStatusEnum.AVAILABLE)
                .stream()
                .map(seatMapper::toResponseDTO)
                .toList();
    }

    @Override
    public SeatResponseDTO createSeat(CreateSeatRequestDTO request) {
        // Check if the seat already exists by its seat number (add logic as needed)
        if (seatRepository.existsBySeatNumber(request.getSeatNumber())) {
            throw new SeatAlreadyExistsException(request.getSeatNumber());
        }

        // Convert DTO to Entity using the mapper
        SeatEntity seatEntity = seatMapper.fromCreateRequest(request);

        // Save the seat entity to the database
        seatEntity = seatRepository.save(seatEntity);

        // Publish event for Seat Created
        SeatCreatedEventDTO event = seatMapper.toSeatCreatedEventDTO(seatEntity);
        eventPublisher.publishSeatCreatedEvent(event);

        // Return the response DTO
        return seatMapper.toResponseDTO(seatEntity);
    }

    @Override
    public SeatResponseDTO getSeatById(UUID id) {
        // Retrieve seat from the database
        SeatEntity seatEntity = seatRepository.findById(id)
                .orElseThrow(() -> new SeatNotFoundException(id));

        // Return the response DTO
        return seatMapper.toResponseDTO(seatEntity);
    }

    @Override
    public List<SeatResponseDTO> getSeatsByCoachId(UUID coachId) {
        // Retrieve list of seats by coach ID
        List<SeatEntity> seatEntities = seatRepository.findByCoachId(coachId);

        // If no seats found, throw an exception
        if (seatEntities.isEmpty()) {
            throw new SeatNotFoundException(coachId);
        }

        // Convert to DTOs and return
        return seatEntities.stream()
                .map(seatMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<SeatResponseDTO> getAllSeats() {
        // Retrieve all seats
        List<SeatEntity> seatEntities = seatRepository.findAll();

        // Convert to DTOs and return
        return seatEntities.stream()
                .map(seatMapper::toResponseDTO)
                .toList();
    }

    @Override
    public SeatResponseDTO updateSeat(UUID id, UpdateSeatRequestDTO request) {
        // Retrieve seat entity from the database
        SeatEntity existingSeat = seatRepository.findById(id)
                .orElseThrow(() -> new SeatNotFoundException(id));

        // Apply partial updates using the mapper
        seatMapper.partialUpdate(existingSeat, request);

        // Save the updated seat entity
        existingSeat = seatRepository.save(existingSeat);

        // Publish event for Seat Updated
        SeatUpdatedEventDTO event = seatMapper.toSeatUpdatedEventDTO(existingSeat);
        eventPublisher.publishSeatUpdatedEvent(event);

        // Return the updated response DTO
        return seatMapper.toResponseDTO(existingSeat);
    }

    @Override
    public void deleteSeat(DeleteSeatRequestDTO request) {
        // Check if the seat exists in the database
        SeatEntity seatEntity = seatRepository.findById(request.getSeatId())
                .orElseThrow(() -> new SeatNotFoundException(request.getSeatId()));

        // Delete the seat entity
        seatRepository.delete(seatEntity);

        // Publish event for Seat Deleted
        SeatDeletedEventDTO event = seatMapper.toSeatDeletedEventDTO(seatEntity);
        eventPublisher.publishSeatDeletedEvent(event);
    }
}