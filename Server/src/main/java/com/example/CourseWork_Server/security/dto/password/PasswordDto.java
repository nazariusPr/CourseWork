package com.example.CourseWork_Server.security.dto.password;

import static com.example.CourseWork_Server.constant.AppConstants.PASSWORD_REGEX;
import static com.example.CourseWork_Server.constant.TextResourceCode.INVALID_PASSWORD;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class PasswordDto {
  @Pattern(regexp = PASSWORD_REGEX, message = INVALID_PASSWORD)
  @NotBlank
  private String password;

  @Pattern(regexp = PASSWORD_REGEX, message = INVALID_PASSWORD)
  @NotBlank
  private String confirmPassword;
}
