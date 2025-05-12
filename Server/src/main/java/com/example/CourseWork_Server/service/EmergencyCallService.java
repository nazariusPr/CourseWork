package com.example.CourseWork_Server.service;

import com.example.CourseWork_Server.dto.emergency.EmergencyCallDto;
import com.example.CourseWork_Server.dto.location.CoordinatesDto;
import java.util.List;

public interface EmergencyCallService {
  /**
   * Retrieves a list of emergency numbers for the specified country.
   *
   * @param countryName the name of the country to search for emergency numbers.
   * @return a list of {@link EmergencyCallDto} objects corresponding to the emergency numbers in
   *     the given country.
   */
  List<EmergencyCallDto> findByCountry(String countryName);

  /**
   * Retrieves a list of emergency numbers based on the provided geographic coordinates.
   *
   * @param coordinates the {@link CoordinatesDto} object containing latitude and longitude.
   * @return a list of {@link EmergencyCallDto} objects corresponding to the emergency numbers in
   *     the specified location.
   */
  List<EmergencyCallDto> findByCoordinates(CoordinatesDto coordinates);
}
