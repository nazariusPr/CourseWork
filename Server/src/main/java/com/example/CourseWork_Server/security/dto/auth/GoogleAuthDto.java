package com.example.CourseWork_Server.security.dto.auth;

import com.example.CourseWork_Server.enums.UserType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class GoogleAuthDto {
  @NotEmpty private String token;
  private UserType userType;
}
