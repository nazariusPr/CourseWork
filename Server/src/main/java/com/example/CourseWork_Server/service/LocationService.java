package com.example.CourseWork_Server.service;

import com.example.CourseWork_Server.dto.location.CoordinatesDto;
import com.example.CourseWork_Server.dto.location.LocationDto;
import java.util.Locale;

public interface LocationService {

  /**
   * Get the location information based on the provided coordinates and locale.
   *
   * @param coordinatesDto the coordinates object containing latitude and longitude.
   * @param locale the locale to determine the language of the returned location data.
   * @return a {@link LocationDto} object containing location details such as address, name, etc.
   */
  LocationDto getLocationDto(CoordinatesDto coordinatesDto, Locale locale);

  /**
   * Generates a human-readable location message based on the provided coordinates and locale. The
   * message is formatted by combining location parts such as city, district, state, road, etc.
   *
   * @param coordinatesDto the coordinates object containing latitude and longitude.
   * @param locale the locale to determine the language of the generated message.
   * @return a formatted string representing the location message.
   */
  String getLocationMessage(CoordinatesDto coordinatesDto, Locale locale);
}
