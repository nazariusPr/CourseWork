package com.example.CourseWork_Server.dto.quickmessage;

import com.example.CourseWork_Server.dto.location.CoordinatesDto;
import com.example.CourseWork_Server.enums.QuickMessageType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class QuickMessageDto {
  private QuickMessageType quickMessageType;
  @NotNull private CoordinatesDto coordinatesDto;
}
