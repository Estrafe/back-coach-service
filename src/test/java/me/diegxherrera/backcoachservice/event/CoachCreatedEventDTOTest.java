package me.diegxherrera.backcoachservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.diegxherrera.backcoachservice.dto.event.CoachCreatedEventDTO;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CoachCreatedEventDTOTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testAllArgsConstructorAndGetters() {
        UUID coachId = UUID.randomUUID();
        UUID trainId = UUID.randomUUID();
        CoachCreatedEventDTO dto = new CoachCreatedEventDTO(
                coachId,
                5,
                trainId,
                "FIRST_CLASS"
        );

        assertEquals(coachId, dto.getId());
        assertEquals(5, dto.getCoachNumber());
        assertEquals(trainId, dto.getTrainId());
        assertEquals("FIRST_CLASS", dto.getCoachType());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        UUID coachId = UUID.randomUUID();
        UUID trainId = UUID.randomUUID();

        CoachCreatedEventDTO dto = new CoachCreatedEventDTO();
        dto.setId(coachId);
        dto.setCoachNumber(3);
        dto.setTrainId(trainId);
        dto.setCoachType("SECOND_CLASS");

        assertEquals(coachId, dto.getId());
        assertEquals(3, dto.getCoachNumber());
        assertEquals(trainId, dto.getTrainId());
        assertEquals("SECOND_CLASS", dto.getCoachType());
    }

    @Test
    void testJsonSerialization() throws Exception {
        CoachCreatedEventDTO dto = new CoachCreatedEventDTO(
                UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
                10,
                UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"),
                "FIRST_CLASS"
        );

        String json = objectMapper.writeValueAsString(dto);

        assertTrue(json.contains("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"));
        assertTrue(json.contains("\"coachNumber\":10"));
        assertTrue(json.contains("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"));
        assertTrue(json.contains("FIRST_CLASS"));
    }

    @Test
    void testJsonDeserialization() throws Exception {
        String json = """
            {
              "id":"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa",
              "coachNumber":10,
              "trainId":"bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb",
              "coachType":"FIRST_CLASS"
            }
        """;

        CoachCreatedEventDTO dto = objectMapper.readValue(json, CoachCreatedEventDTO.class);

        assertEquals(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"), dto.getId());
        assertEquals(10, dto.getCoachNumber());
        assertEquals(UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"), dto.getTrainId());
        assertEquals("FIRST_CLASS", dto.getCoachType());
    }
}