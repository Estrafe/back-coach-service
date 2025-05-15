package me.diegxherrera.backcoachservice.mapper;

import me.diegxherrera.backcoachservice.dto.request.CreateSeatRequestDTO;
import me.diegxherrera.backcoachservice.dto.request.UpdateSeatRequestDTO;
import me.diegxherrera.backcoachservice.dto.response.SeatResponseDTO;
import me.diegxherrera.backcoachservice.dto.event.*;
import me.diegxherrera.backcoachservice.model.SeatEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SeatMapper {

    SeatEntity fromCreateRequest(CreateSeatRequestDTO dto);

    SeatResponseDTO toResponseDTO(SeatEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget SeatEntity entity, UpdateSeatRequestDTO dto);

    // Event DTOs
    SeatCreatedEventDTO toSeatCreatedEventDTO(SeatEntity entity);

    SeatUpdatedEventDTO toSeatUpdatedEventDTO(SeatEntity entity);

    SeatDeletedEventDTO toSeatDeletedEventDTO(SeatEntity entity);
}