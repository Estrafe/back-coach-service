package me.diegxherrera.backcoachservice.service.impl;

import me.diegxherrera.backcoachservice.dto.event.CoachCreatedEventDTO;
import me.diegxherrera.backcoachservice.dto.event.CoachUpdatedEventDTO;
import me.diegxherrera.backcoachservice.dto.event.CoachDeletedEventDTO;
import me.diegxherrera.backcoachservice.dto.request.CreateCoachRequestDTO;
import me.diegxherrera.backcoachservice.dto.request.UpdateCoachRequestDTO;
import me.diegxherrera.backcoachservice.dto.response.CoachResponseDTO;
import me.diegxherrera.backcoachservice.event.EventPublisher;
import me.diegxherrera.backcoachservice.mapper.CoachMapper;
import me.diegxherrera.backcoachservice.model.CoachEntity;
import me.diegxherrera.backcoachservice.repository.CoachRepository;
import me.diegxherrera.backcoachservice.service.CoachService;
import me.diegxherrera.backcoachservice.exception.CoachNotFoundException;
import me.diegxherrera.backcoachservice.exception.CoachAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CoachServiceImpl implements CoachService {

    private final CoachRepository coachRepository;
    private final CoachMapper coachMapper;
    private final EventPublisher eventPublisher;

    @Autowired
    public CoachServiceImpl(CoachRepository coachRepository, CoachMapper coachMapper, EventPublisher eventPublisher) {
        this.coachRepository = coachRepository;
        this.coachMapper = coachMapper;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public CoachResponseDTO createCoach(CreateCoachRequestDTO request) {
        // Check if a coach with the same coachNumber already exists (to prevent duplicates)
        if (coachRepository.existsByCoachNumber(request.getCoachNumber())) {
            throw new CoachAlreadyExistsException("Coach with number " + request.getCoachNumber() + " already exists.");
        }

        // Convert DTO to Entity using the mapper
        CoachEntity coachEntity = coachMapper.fromCreateRequest(request);

        // Save the coach entity to the database
        coachEntity = coachRepository.save(coachEntity);

        // Publish event for Coach Created
        CoachCreatedEventDTO event = coachMapper.toCoachCreatedEventDTO(coachEntity);
        eventPublisher.publishCoachCreatedEvent(event);

        // Return the response DTO
        return coachMapper.toResponseDTO(coachEntity);
    }

    @Override
    public CoachResponseDTO getCoachById(UUID id) {
        // Retrieve coach from the database
        CoachEntity coachEntity = coachRepository.findById(id).orElseThrow(() -> new CoachNotFoundException(id));

        // Return the response DTO
        return coachMapper.toResponseDTO(coachEntity);
    }

    @Override
    public List<CoachResponseDTO> getCoachesByTrainId(UUID trainId) {
        // Retrieve list of coaches by train ID
        List<CoachEntity> coachEntities = coachRepository.findByTrainId(trainId);

        // Convert to DTOs and return
        return coachEntities.stream()
                .map(coachMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<CoachResponseDTO> getAllCoaches() {
        // Retrieve all coaches
        List<CoachEntity> coachEntities = coachRepository.findAll();

        // Convert to DTOs and return
        return coachEntities.stream()
                .map(coachMapper::toResponseDTO)
                .toList();
    }

    @Override
    public CoachResponseDTO updateCoach(UUID id, UpdateCoachRequestDTO request) {
        // Retrieve coach entity from the database
        CoachEntity existingCoach = coachRepository.findById(id).orElseThrow(() -> new CoachNotFoundException(id));

        // Apply partial updates using the mapper
        coachMapper.partialUpdate(existingCoach, request);

        // Save the updated coach entity
        existingCoach = coachRepository.save(existingCoach);

        // Publish event for Coach Updated
        CoachUpdatedEventDTO event = coachMapper.toCoachUpdatedEventDTO(existingCoach);
        eventPublisher.publishCoachUpdatedEvent(event);

        // Return the updated response DTO
        return coachMapper.toResponseDTO(existingCoach);
    }

    @Override
    public void deleteCoach(UUID id) {
        // Check if the coach exists in the database
        CoachEntity coachEntity = coachRepository.findById(id).orElseThrow(() -> new CoachNotFoundException(id));

        // Delete the coach entity
        coachRepository.delete(coachEntity);

        // Publish event for Coach Deleted
        CoachDeletedEventDTO event = coachMapper.toCoachDeletedEventDTO(coachEntity);
        eventPublisher.publishCoachDeletedEvent(event);
    }
}