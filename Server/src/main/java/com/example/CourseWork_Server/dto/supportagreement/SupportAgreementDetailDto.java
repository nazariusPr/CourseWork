package com.example.CourseWork_Server.dto.supportagreement;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class SupportAgreementDetailDto extends SupportAgreementDto {
  @NotNull private UUID id;
  @NotBlank private String supportProviderEmail;
  @NotBlank private String visuallyImpairedEmail;
  @Past private LocalDateTime agreementDate;
}
