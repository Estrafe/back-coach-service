package me.diegxherrera.backcoachservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.diegxherrera.backcoachservice.dto.request.CreateCoachRequestDTO;
import me.diegxherrera.backcoachservice.dto.request.UpdateCoachRequestDTO;
import me.diegxherrera.backcoachservice.model.CoachTypeEnum;
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
public class CoachIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void fullCoachLifecycle() throws Exception {
        // Create
        CreateCoachRequestDTO createRequest = CreateCoachRequestDTO.builder()
                .trainId(UUID.randomUUID())
                .coachNumber(10)
                .coachType(CoachTypeEnum.FIRST_CLASS)
                .build();

        String response = mockMvc.perform(post("/coaches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.coachNumber", is(10)))
                .andExpect(jsonPath("$.coachType", is("FIRST_CLASS")))
                .andReturn().getResponse().getContentAsString();

        UUID coachId = UUID.fromString(objectMapper.readTree(response).get("coachId").asText());

        // Update
        UpdateCoachRequestDTO updateRequest = UpdateCoachRequestDTO.builder()
                .coachNumber(15)
                .coachType(CoachTypeEnum.FIRST_CLASS)
                .build();

        mockMvc.perform(put("/coaches/{id}", coachId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.coachNumber", is(15)))
                .andExpect(jsonPath("$.coachType", is("SECOND_CLASS")));

        // Get
        mockMvc.perform(get("/coaches/{id}", coachId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.coachNumber", is(15)))
                .andExpect(jsonPath("$.coachType", is("SECOND_CLASS")));

        // Delete
        mockMvc.perform(delete("/coaches/{id}", coachId))
                .andExpect(status().isNoContent());

        // Confirm not found
        mockMvc.perform(get("/coaches/{id}", coachId))
                .andExpect(status().isNotFound());
    }
}