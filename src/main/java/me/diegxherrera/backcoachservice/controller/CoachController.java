package me.diegxherrera.backcoachservice.controller;

import me.diegxherrera.backcoachservice.dto.request.CreateCoachRequestDTO;
import me.diegxherrera.backcoachservice.dto.request.UpdateCoachRequestDTO;
import me.diegxherrera.backcoachservice.dto.response.CoachResponseDTO;
import me.diegxherrera.backcoachservice.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/coaches")
public class CoachController {

    private final CoachService coachService;

    @Autowired
    public CoachController(CoachService coachService) {
        this.coachService = coachService;
    }

    // Create a new coach
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CoachResponseDTO createCoach(@RequestBody CreateCoachRequestDTO request) {
        return coachService.createCoach(request);
    }

    // Get a coach by its ID
    @GetMapping("/{id}")
    public CoachResponseDTO getCoachById(@PathVariable UUID id) {
        return coachService.getCoachById(id);
    }

    // Get all coaches for a specific train
    @GetMapping("/train/{trainId}")
    public List<CoachResponseDTO> getCoachesByTrainId(@PathVariable UUID trainId) {
        return coachService.getCoachesByTrainId(trainId);
    }

    // Get all coaches
    @GetMapping
    public List<CoachResponseDTO> getAllCoaches() {
        return coachService.getAllCoaches();
    }

    // Update an existing coach
    @PutMapping("/{id}")
    public CoachResponseDTO updateCoach(@PathVariable UUID id, @RequestBody UpdateCoachRequestDTO request) {
        return coachService.updateCoach(id, request);
    }

    // Delete a coach
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCoach(@PathVariable UUID id) {
        coachService.deleteCoach(id);
    }
}