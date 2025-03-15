package com.example.CourseWork_Server.mapper;

import com.example.CourseWork_Server.dto.location.CoordinatesDto;
import com.example.CourseWork_Server.dto.quickmessage.QuickMessageDetailDto;
import com.example.CourseWork_Server.model.QuickMessage;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuickMessageMapper {
  @Mapping(source = "detailDto.coordinatesDto.latitude", target = "latitude")
  @Mapping(source = "detailDto.coordinatesDto.longitude", target = "longitude")
  QuickMessage toEntity(QuickMessageDetailDto detailDto);

  @Mapping(target = "coordinatesDto", expression = "java(mapCoordinates(quickMessage))")
  QuickMessageDetailDto toDetailDto(QuickMessage quickMessage);

  default CoordinatesDto mapCoordinates(QuickMessage quickMessage) {
    return new CoordinatesDto(quickMessage.getLatitude(), quickMessage.getLongitude());
  }
}
