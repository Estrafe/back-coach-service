package me.diegxherrera.backcoachservice.service;

import me.diegxherrera.backcoachservice.dto.request.CreateCoachRequestDTO;
import me.diegxherrera.backcoachservice.dto.request.UpdateCoachRequestDTO;
import me.diegxherrera.backcoachservice.dto.response.CoachResponseDTO;

import java.util.List;
import java.util.UUID;

public interface CoachService {
    CoachResponseDTO createCoach(CreateCoachRequestDTO request);
    CoachResponseDTO getCoachById(UUID id);
    List<CoachResponseDTO> getCoachesByTrainId(UUID trainId);  // Get coaches by train ID
    List<CoachResponseDTO> getAllCoaches();
    CoachResponseDTO updateCoach(UUID id, UpdateCoachRequestDTO request);
    void deleteCoach(UUID id);
}