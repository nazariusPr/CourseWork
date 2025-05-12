package com.example.CourseWork_Server.service;

import com.example.CourseWork_Server.dto.location.CoordinatesDto;
import com.example.CourseWork_Server.model.Country;

public interface CountryService {
  /**
   * Retrieves a {@link Country} entity by its name.
   *
   * @param countryName the name of the country to search for, must not be {@code null} or empty
   * @return the {@link Country} entity that matches the given name
   * @throws jakarta.persistence.EntityNotFoundException if no country with the specified name is
   *     found
   */
  Country findByName(String countryName);

  /**
   * Determines and retrieves the {@link Country} entity based on the provided geographic
   * coordinates.
   *
   * @param coordinatesDto the coordinates containing latitude and longitude
   * @return the {@link Country} entity associated with the given coordinates
   * @throws jakarta.persistence.EntityNotFoundException if no country could be resolved from the
   *     coordinates
   */
  Country findByCoordinates(CoordinatesDto coordinatesDto);
}
