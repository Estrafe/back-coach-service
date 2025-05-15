package me.diegxherrera.backcoachservice.service;

import me.diegxherrera.backcoachservice.dto.event.CoachCreatedEventDTO;
import me.diegxherrera.backcoachservice.dto.event.CoachDeletedEventDTO;
import me.diegxherrera.backcoachservice.dto.event.CoachUpdatedEventDTO;
import me.diegxherrera.backcoachservice.dto.request.CreateCoachRequestDTO;
import me.diegxherrera.backcoachservice.dto.request.UpdateCoachRequestDTO;
import me.diegxherrera.backcoachservice.event.EventPublisher;
import me.diegxherrera.backcoachservice.mapper.CoachMapper;
import me.diegxherrera.backcoachservice.model.CoachEntity;
import me.diegxherrera.backcoachservice.model.CoachTypeEnum;
import me.diegxherrera.backcoachservice.repository.CoachRepository;
import me.diegxherrera.backcoachservice.service.impl.CoachServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CoachServiceEventPublishTest {

    private CoachServiceImpl coachService;
    private EventPublisher eventPublisher;
    private CoachRepository coachRepository;
    private CoachMapper coachMapper;

    @BeforeEach
    void setUp() {
        coachRepository = mock(CoachRepository.class);
        eventPublisher = mock(EventPublisher.class);
        coachMapper = mock(CoachMapper.class);
        coachService = new CoachServiceImpl(coachRepository, coachMapper, eventPublisher);
    }

    @Test
    void testPublishCoachCreatedEvent() {
        CreateCoachRequestDTO request = CreateCoachRequestDTO.builder()
                .coachNumber(1)
                .coachType(CoachTypeEnum.FIRST_CLASS)
                .trainId(UUID.randomUUID())
                .build();

        CoachEntity coachEntity = requestToEntity(request);
        when(coachMapper.fromCreateRequest(any())).thenReturn(coachEntity);
        when(coachRepository.save(any())).thenReturn(coachEntity);

        coachService.createCoach(request);

        ArgumentCaptor<CoachCreatedEventDTO> captor = ArgumentCaptor.forClass(CoachCreatedEventDTO.class);
        verify(eventPublisher).publishCoachCreatedEvent(captor.capture());

        assertThat(captor.getValue().getCoachNumber()).isEqualTo(1);
        assertThat(captor.getValue().getCoachType()).isEqualTo(CoachTypeEnum.FIRST_CLASS);
    }

    @Test
    void testPublishCoachUpdatedEvent() {
        UUID coachId = UUID.randomUUID();

        CoachEntity original = requestToEntity(CreateCoachRequestDTO.builder()
                .coachNumber(1)
                .coachType(CoachTypeEnum.FIRST_CLASS)
                .trainId(UUID.randomUUID())
                .build());
        original.setId(coachId);

        when(coachRepository.findById(coachId)).thenReturn(Optional.of(original));
        when(coachRepository.save(any())).thenReturn(original);

        UpdateCoachRequestDTO update = UpdateCoachRequestDTO.builder()
                .coachNumber(2)
                .coachType(CoachTypeEnum.FIRST_CLASS)
                .build();

        coachService.updateCoach(coachId, update);

        verify(eventPublisher).publishCoachUpdatedEvent(any(CoachUpdatedEventDTO.class));
    }

    @Test
    void testPublishCoachDeletedEvent() {
        UUID coachId = UUID.randomUUID();

        CoachEntity coach = requestToEntity(CreateCoachRequestDTO.builder()
                .coachNumber(3)
                .coachType(CoachTypeEnum.STANDARD)
                .trainId(UUID.randomUUID())
                .build());
        coach.setId(coachId);

        when(coachRepository.findById(coachId)).thenReturn(Optional.of(coach));

        coachService.deleteCoach(coachId);

        ArgumentCaptor<CoachDeletedEventDTO> captor = ArgumentCaptor.forClass(CoachDeletedEventDTO.class);
        verify(eventPublisher).publishCoachDeletedEvent(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo(coachId);
    }

    // Helper to simulate mapper output
    private CoachEntity requestToEntity(CreateCoachRequestDTO dto) {
        return CoachEntity.builder()
                .coachNumber(dto.getCoachNumber())
                .coachType(dto.getCoachType())
                .trainId(dto.getTrainId())
                .seatIds(List.of())
                .build();
    }
}