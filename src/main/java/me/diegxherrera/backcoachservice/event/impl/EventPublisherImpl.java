package me.diegxherrera.backcoachservice.event.impl;

import me.diegxherrera.backcoachservice.config.RabbitMQConfig;
import me.diegxherrera.backcoachservice.dto.event.*;
import me.diegxherrera.backcoachservice.event.EventPublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventPublisherImpl implements EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public EventPublisherImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishCoachCreatedEvent(CoachCreatedEventDTO event) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.COACH_EXCHANGE, RabbitMQConfig.COACH_CREATED_ROUTING_KEY, event);
    }

    @Override
    public void publishCoachUpdatedEvent(CoachUpdatedEventDTO event) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.COACH_EXCHANGE, RabbitMQConfig.COACH_UPDATED_ROUTING_KEY, event);
    }

    @Override
    public void publishCoachDeletedEvent(CoachDeletedEventDTO event) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.COACH_EXCHANGE, RabbitMQConfig.COACH_DELETED_ROUTING_KEY, event);
    }

    @Override
    public void publishSeatCreatedEvent(SeatCreatedEventDTO event) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.SEAT_EXCHANGE, RabbitMQConfig.SEAT_CREATED_ROUTING_KEY, event);
    }

    @Override
    public void publishSeatUpdatedEvent(SeatUpdatedEventDTO event) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.SEAT_EXCHANGE, RabbitMQConfig.SEAT_UPDATED_ROUTING_KEY, event);
    }

    @Override
    public void publishSeatDeletedEvent(SeatDeletedEventDTO event) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.SEAT_EXCHANGE, RabbitMQConfig.SEAT_DELETED_ROUTING_KEY, event);
    }
}