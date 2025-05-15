package me.diegxherrera.backcoachservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.diegxherrera.backcoachservice.dto.request.CreateCoachRequestDTO;
import me.diegxherrera.backcoachservice.model.CoachTypeEnum;
import me.diegxherrera.backcoachservice.repository.CoachRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CoachServiceDuplicateTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private CoachRepository coachRepository;

    private UUID trainId;
    private int coachNumber;

    @BeforeEach
    void setUp() throws Exception {
        coachRepository.deleteAll();

        trainId = UUID.randomUUID();
        coachNumber = 5;

        CreateCoachRequestDTO request = CreateCoachRequestDTO.builder()
                .trainId(trainId)
                .coachNumber(coachNumber)
                .coachType(CoachTypeEnum.FIRST_CLASS)
                .build();

        mockMvc.perform(post("/coaches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateCoach_WithDuplicateNumberInSameTrain_ShouldFail() throws Exception {
        CreateCoachRequestDTO duplicateRequest = CreateCoachRequestDTO.builder()
                .trainId(trainId) // Same train
                .coachNumber(coachNumber) // Same coach number
                .coachType(CoachTypeEnum.FIRST_CLASS)
                .build();

        mockMvc.perform(post("/coaches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duplicateRequest)))
                .andExpect(status().isConflict()); // Expecting 409
    }

    @Test
    void testCreateCoach_SameCoachNumberInDifferentTrain_ShouldPass() throws Exception {
        CreateCoachRequestDTO request = CreateCoachRequestDTO.builder()
                .trainId(UUID.randomUUID()) // Different train
                .coachNumber(coachNumber) // Same coach number, different train
                .coachType(CoachTypeEnum.FIRST_CLASS)
                .build();

        mockMvc.perform(post("/coaches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}