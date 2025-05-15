package me.diegxherrera.backcoachservice.event;

import me.diegxherrera.backcoachservice.config.RabbitMQConfig;
import me.diegxherrera.backcoachservice.dto.event.*;
import me.diegxherrera.backcoachservice.event.impl.EventPublisherImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EventPublisherImplTest {

    private RabbitTemplate rabbitTemplate;
    private EventPublisherImpl eventPublisher;

    @BeforeEach
    void setUp() {
        rabbitTemplate = mock(RabbitTemplate.class);
        eventPublisher = new EventPublisherImpl(rabbitTemplate);
    }

    @Test
    void testPublishCoachCreatedEvent() {
        CoachCreatedEventDTO event = CoachCreatedEventDTO.builder()
                .coachNumber(7)
                .trainId(UUID.randomUUID())
                .coachNumber(1)
                .coachType("FIRST_CLASS")
                .build();

        eventPublisher.publishCoachCreatedEvent(event);

        verify(rabbitTemplate).convertAndSend(
                RabbitMQConfig.COACH_EXCHANGE,
                RabbitMQConfig.COACH_CREATED_ROUTING_KEY,
                event
        );
    }

    @Test
    void testPublishCoachUpdatedEvent() {
        CoachUpdatedEventDTO event = CoachUpdatedEventDTO.builder()
                .id(UUID.randomUUID())
                .trainId(UUID.randomUUID())
                .coachNumber(2)
                .coachType("SECOND_CLASS")
                .build();

        eventPublisher.publishCoachUpdatedEvent(event);

        verify(rabbitTemplate).convertAndSend(
                RabbitMQConfig.COACH_EXCHANGE,
                RabbitMQConfig.COACH_UPDATED_ROUTING_KEY,
                event
        );
    }

    @Test
    void testPublishCoachDeletedEvent() {
        CoachDeletedEventDTO event = new CoachDeletedEventDTO(UUID.randomUUID());

        eventPublisher.publishCoachDeletedEvent(event);

        verify(rabbitTemplate).convertAndSend(
                RabbitMQConfig.COACH_EXCHANGE,
                RabbitMQConfig.COACH_DELETED_ROUTING_KEY,
                event
        );
    }

    @Test
    void testPublishSeatCreatedEvent() {
        SeatCreatedEventDTO event = SeatCreatedEventDTO.builder()
                .id(UUID.randomUUID())
                .coachId(UUID.randomUUID())
                .seatNumber(5)
                .seatClass("ECONOMY")
                .status("AVAILABLE")
                .build();

        eventPublisher.publishSeatCreatedEvent(event);

        verify(rabbitTemplate).convertAndSend(
                RabbitMQConfig.SEAT_EXCHANGE,
                RabbitMQConfig.SEAT_CREATED_ROUTING_KEY,
                event
        );
    }

    @Test
    void testPublishSeatUpdatedEvent() {
        SeatUpdatedEventDTO event = SeatUpdatedEventDTO.builder()
                .id(UUID.randomUUID())
                .coachId(UUID.randomUUID())
                .seatNumber(6)
                .seatClass("BUSINESS")
                .status("RESERVED")
                .build();

        eventPublisher.publishSeatUpdatedEvent(event);

        verify(rabbitTemplate).convertAndSend(
                RabbitMQConfig.SEAT_EXCHANGE,
                RabbitMQConfig.SEAT_UPDATED_ROUTING_KEY,
                event
        );
    }

    @Test
    void testPublishSeatDeletedEvent() {
        SeatDeletedEventDTO event = new SeatDeletedEventDTO(UUID.randomUUID());

        eventPublisher.publishSeatDeletedEvent(event);

        verify(rabbitTemplate).convertAndSend(
                RabbitMQConfig.SEAT_EXCHANGE,
                RabbitMQConfig.SEAT_DELETED_ROUTING_KEY,
                event
        );
    }
}