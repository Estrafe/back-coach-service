package me.diegxherrera.backcoachservice.controller;

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
class CoachControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private CoachRepository coachRepository;

    private UUID coachId;
    private int coachNumber;
    private UUID trainId;

    @BeforeEach
    void setUp() throws Exception {
        coachRepository.deleteAll();

        trainId = UUID.randomUUID();
        coachNumber = 1;

        CreateCoachRequestDTO createDto = CreateCoachRequestDTO.builder()
                .coachNumber(coachNumber)
                .trainId(trainId)
                .coachType(CoachTypeEnum.FIRST_CLASS)
                .build();

        String response = mockMvc.perform(post("/coaches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.coachNumber", is(coachNumber)))
                .andReturn().getResponse().getContentAsString();

        coachId = UUID.fromString(objectMapper.readTree(response).get("coachId").asText());
    }

    @Test
    void testGetCoachById_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/coaches/{id}", coachId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.coachId", is(coachId.toString())))
                .andExpect(jsonPath("$.coachNumber", is(coachNumber)));
    }

    @Test
    void testGetCoachByInvalidId_ShouldReturn404() throws Exception {
        mockMvc.perform(get("/coaches/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateCoach_ShouldReturnUpdatedValues() throws Exception {
        UpdateCoachRequestDTO updateDto = UpdateCoachRequestDTO.builder()
                .coachNumber(2)
                .coachType(CoachTypeEnum.FIRST_CLASS)
                .build();

        mockMvc.perform(put("/coaches/{id}", coachId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.coachNumber", is(2)))
                .andExpect(jsonPath("$.coachType", is("FIRST_CLASS")));
    }

    @Test
    void testDeleteCoach_ShouldReturn204AndThen404() throws Exception {
        mockMvc.perform(delete("/coaches/{id}", coachId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/coaches/{id}", coachId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllCoaches_ShouldReturnList() throws Exception {
        mockMvc.perform(get("/coaches"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())));
    }

    @Test
    void testCreateCoach_InvalidBody_ShouldReturn400() throws Exception {
        mockMvc.perform(post("/coaches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateCoach_MissingCoachType_ShouldReturn400() throws Exception {
        String payload = "{" +
                "\"trainId\": \"" + UUID.randomUUID() + "\"," +
                "\"coachNumber\": 3" +
                "}";

        mockMvc.perform(post("/coaches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.coachType", is("Coach type must not be null")));
    }
}