package me.diegxherrera.backcoachservice.mapper;

import me.diegxherrera.backcoachservice.dto.request.CreateCoachRequestDTO;
import me.diegxherrera.backcoachservice.model.CoachEntity;
import me.diegxherrera.backcoachservice.model.CoachTypeEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CoachMapperSanityCheckTest {

    @Autowired
    private CoachMapper mapper;

    @Test
    void sanityCheckFromDTO() {
        CreateCoachRequestDTO dto = CreateCoachRequestDTO.builder()
                .trainId(UUID.randomUUID())
                .coachNumber(1)
                .coachType(CoachTypeEnum.STANDARD)
                .build();

        CoachEntity entity = mapper.fromCreateRequest(dto);

        System.out.println("Mapped CoachEntity.coachType = " + entity.getCoachType());

        assertNotNull(entity);
        assertEquals(dto.getCoachType(), entity.getCoachType());
        assertEquals(dto.getCoachNumber(), entity.getCoachNumber());
        assertEquals(dto.getTrainId(), entity.getTrainId());
    }
}