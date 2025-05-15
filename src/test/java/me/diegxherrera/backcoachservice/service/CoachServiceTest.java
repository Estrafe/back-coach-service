package me.diegxherrera.backcoachservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.diegxherrera.backcoachservice.dto.request.CreateCoachRequestDTO;
import me.diegxherrera.backcoachservice.dto.request.UpdateCoachRequestDTO;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CoachServiceTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private CoachRepository coachRepository;

    private UUID coachId;

    @BeforeEach
    void setUp() throws Exception {
        coachRepository.deleteAll();

        UUID trainId = UUID.randomUUID();
        int coachNumber = 7;
        CreateCoachRequestDTO createRequest = CreateCoachRequestDTO.builder()
                .trainId(trainId)
                .coachNumber(coachNumber)
                .coachType(CoachTypeEnum.FIRST_CLASS)
                .build();

        String response = mockMvc.perform(post("/coaches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.coachNumber", is(coachNumber)))
                .andReturn().getResponse().getContentAsString();

        JsonNode jsonNode = objectMapper.readTree(response);
        coachId = UUID.fromString(jsonNode.get("coachId").asText());
    }

    @Test
    void testGetCoachById() throws Exception {
        mockMvc.perform(get("/coaches/{id}", coachId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.coachId", is(coachId.toString())));
    }

    @Test
    void testUpdateCoach() throws Exception {
        UpdateCoachRequestDTO updateRequest = UpdateCoachRequestDTO.builder()
                .coachNumber(88)
                .coachType(CoachTypeEnum.SLEEPER)
                .build();

        mockMvc.perform(put("/coaches/{id}", coachId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.coachNumber", is(88)))
                .andExpect(jsonPath("$.coachType", is("SLEEPER")));
    }

    @Test
    void testGetAllCoaches() throws Exception {
        mockMvc.perform(get("/coaches"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())));
    }

    @Test
    void testDeleteCoach() throws Exception {
        mockMvc.perform(delete("/coaches/{id}", coachId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/coaches/{id}", coachId))
                .andExpect(status().isNotFound());
    }
}