package com.example.CourseWork_Server.service.impl;

import com.example.CourseWork_Server.dto.location.CoordinatesDto;
import com.example.CourseWork_Server.model.Country;
import com.example.CourseWork_Server.repository.CountryRepository;
import com.example.CourseWork_Server.service.CountryService;
import com.example.CourseWork_Server.service.LocationService;
import com.example.CourseWork_Server.utils.ExceptionUtils;
import java.util.Locale;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {
  private final LocationService locationService;
  private final CountryRepository repository;
  private final ExceptionUtils exceptionUtils;

  /** {@inheritDoc} */
  @Override
  public Country findByName(String countryName) {
    return repository
        .findByName(countryName)
        .orElseThrow(() -> exceptionUtils.entityNotFoundException("Country", countryName));
  }

  /** {@inheritDoc} */
  @Override
  public Country findByCoordinates(CoordinatesDto coordinatesDto) {
    String countryName = locationService.getCountryName(coordinatesDto, Locale.ENGLISH);
    return findByName(countryName);
  }
}
