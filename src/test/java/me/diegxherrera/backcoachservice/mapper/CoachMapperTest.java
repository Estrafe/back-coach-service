package me.diegxherrera.backcoachservice.mapper;

import me.diegxherrera.backcoachservice.dto.request.CreateCoachRequestDTO;
import me.diegxherrera.backcoachservice.dto.request.UpdateCoachRequestDTO;
import me.diegxherrera.backcoachservice.dto.response.CoachResponseDTO;
import me.diegxherrera.backcoachservice.model.CoachEntity;
import me.diegxherrera.backcoachservice.model.CoachTypeEnum;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CoachMapperTest {

    private final CoachMapper mapper = Mappers.getMapper(CoachMapper.class);

    @Test
    void testFromCreateRequest() {
        UUID trainId = UUID.randomUUID();
        CreateCoachRequestDTO request = CreateCoachRequestDTO.builder()
                .trainId(trainId)
                .coachNumber(1)
                .coachType(CoachTypeEnum.STANDARD)
                .build();

        CoachEntity entity = mapper.fromCreateRequest(request);

        assertNotNull(entity);
        assertEquals(trainId, entity.getTrainId());
        assertEquals(1, entity.getCoachNumber());
        assertEquals(CoachTypeEnum.STANDARD, entity.getCoachType());
    }

    @Test
    void testPartialUpdate() {
        CoachEntity entity = CoachEntity.builder()
                .id(UUID.randomUUID())
                .trainId(UUID.randomUUID())
                .coachNumber(5)
                .coachType(CoachTypeEnum.FIRST_CLASS)
                .seatIds(List.of(UUID.randomUUID()))
                .build();

        UpdateCoachRequestDTO update = UpdateCoachRequestDTO.builder()
                .coachNumber(8)
                .coachType(CoachTypeEnum.STANDARD)
                .build();

        mapper.partialUpdate(entity, update);

        assertEquals(8, entity.getCoachNumber());
        assertEquals(CoachTypeEnum.STANDARD, entity.getCoachType());
    }

    @Test
    void testToResponseDTO() {
        UUID coachId = UUID.randomUUID();
        UUID trainId = UUID.randomUUID();
        List<UUID> seatIds = List.of(UUID.randomUUID(), UUID.randomUUID());

        CoachEntity entity = CoachEntity.builder()
                .id(coachId)
                .trainId(trainId)
                .coachNumber(2)
                .coachType(CoachTypeEnum.STANDARD)
                .seatIds(seatIds)
                .build();

        CoachResponseDTO dto = mapper.toResponseDTO(entity);

        assertEquals(coachId, dto.getId());
        assertEquals(trainId, dto.getTrainId());
        assertEquals(2, dto.getCoachNumber());
        assertEquals(CoachTypeEnum.STANDARD, dto.getCoachType());
        assertEquals(seatIds, dto.getSeatIds());
    }
}