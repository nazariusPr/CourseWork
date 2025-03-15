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
public class LocationDto {
  private long placeId;
  private String licence;
  private String osmType;
  private long osmId;
  private double lat;
  private double lon;
  private String locationClass;
  private String type;
  private int placeRank;
  private double importance;
  private String addresstype;
  private String name;
  private String displayName;
  private AddressDto address;
  private String[] boundingBox;
}
