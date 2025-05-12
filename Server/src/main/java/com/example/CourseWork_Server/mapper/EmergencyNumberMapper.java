package com.example.CourseWork_Server.mapper;

import com.example.CourseWork_Server.dto.emergency.EmergencyCallDto;
import com.example.CourseWork_Server.model.EmergencyCall;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmergencyNumberMapper {
  @Mapping(source = "emergencyCall.emergencyService.name", target = "emergencyService")
  @Mapping(source = "emergencyCall.country.name", target = "country")
  EmergencyCallDto toDto(EmergencyCall emergencyCall);
}
