package me.diegxherrera.backcoachservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.diegxherrera.backcoachservice.dto.request.CreateCoachRequestDTO;
import me.diegxherrera.backcoachservice.model.CoachTypeEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CoachServiceValidationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void testCreateCoach_MissingTrainId_ShouldFail() throws Exception {
        CreateCoachRequestDTO request = CreateCoachRequestDTO.builder()
                .coachNumber(1)
                .coachType(CoachTypeEnum.STANDARD)
                .build();

        mockMvc.perform(post("/coaches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.trainId").exists());
    }

    @Test
    void testCreateCoach_MissingCoachType_ShouldFail() throws Exception {
        CreateCoachRequestDTO request = CreateCoachRequestDTO.builder()
                .trainId(UUID.randomUUID())
                .coachNumber(2)
                .build();

        mockMvc.perform(post("/coaches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.coachType").exists());
    }

    @Test
    void testCreateCoach_InvalidCoachNumber_ShouldFail() throws Exception {
        CreateCoachRequestDTO request = CreateCoachRequestDTO.builder()
                .trainId(UUID.randomUUID())
                .coachNumber(0)  // invalid because min is 1
                .coachType(CoachTypeEnum.STANDARD)
                .build();

        mockMvc.perform(post("/coaches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.coachNumber").exists());
    }

    @Test
    void testCreateCoach_NullPayload_ShouldFail() throws Exception {
        mockMvc.perform(post("/coaches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }
}