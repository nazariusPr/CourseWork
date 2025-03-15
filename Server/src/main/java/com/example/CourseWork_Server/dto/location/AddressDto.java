package com.example.CourseWork_Server.dto.location;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AddressDto {
  private String road;
  private String neighbourhood;
  private String suburb;
  private String borough;
  private String city;
  private String municipality;
  private String district;
  private String state;
  private String isoCode;
  private String postcode;
  private String country;
  private String countryCode;
}
