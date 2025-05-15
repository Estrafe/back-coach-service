package me.diegxherrera.backcoachservice.event;

import me.diegxherrera.backcoachservice.dto.event.*;

public interface EventPublisher {

    void publishCoachCreatedEvent(CoachCreatedEventDTO event);

    void publishCoachUpdatedEvent(CoachUpdatedEventDTO event);

    void publishCoachDeletedEvent(CoachDeletedEventDTO event);

    void publishSeatCreatedEvent(SeatCreatedEventDTO event);

    void publishSeatUpdatedEvent(SeatUpdatedEventDTO event);

    void publishSeatDeletedEvent(SeatDeletedEventDTO event);
}