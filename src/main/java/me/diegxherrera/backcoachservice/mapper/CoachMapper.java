package me.diegxherrera.backcoachservice.mapper;

import me.diegxherrera.backcoachservice.dto.request.CreateCoachRequestDTO;
import me.diegxherrera.backcoachservice.dto.request.UpdateCoachRequestDTO;
import me.diegxherrera.backcoachservice.dto.response.CoachResponseDTO;
import me.diegxherrera.backcoachservice.dto.event.*;
import me.diegxherrera.backcoachservice.model.CoachEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CoachMapper {

    CoachEntity fromCreateRequest(CreateCoachRequestDTO dto);

    CoachResponseDTO toResponseDTO(CoachEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget CoachEntity entity, UpdateCoachRequestDTO request);

    // Event DTO mappings
    CoachCreatedEventDTO toCoachCreatedEventDTO(CoachEntity entity);

    CoachUpdatedEventDTO toCoachUpdatedEventDTO(CoachEntity entity);

    CoachDeletedEventDTO toCoachDeletedEventDTO(CoachEntity entity);
}