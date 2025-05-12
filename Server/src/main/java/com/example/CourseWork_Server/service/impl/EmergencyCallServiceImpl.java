package com.example.CourseWork_Server.service.impl;

import com.example.CourseWork_Server.dto.emergency.EmergencyCallDto;
import com.example.CourseWork_Server.dto.location.CoordinatesDto;
import com.example.CourseWork_Server.mapper.EmergencyNumberMapper;
import com.example.CourseWork_Server.model.EmergencyCall;
import com.example.CourseWork_Server.repository.EmergencyCallRepository;
import com.example.CourseWork_Server.service.EmergencyCallService;
import com.example.CourseWork_Server.service.LocationService;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmergencyCallServiceImpl implements EmergencyCallService {
  private final LocationService locationService;
  private final EmergencyCallRepository repository;
  private final EmergencyNumberMapper mapper;

  /** {@inheritDoc} */
  @Override
  public List<EmergencyCallDto> findByCountry(String countryName) {
    List<EmergencyCall> numbers = repository.findByCountryName(countryName);
    return numbers.stream().map(mapper::toDto).collect(Collectors.toList());
  }

  /** {@inheritDoc} */
  @Override
  @Cacheable("emergencyNumber")
  public List<EmergencyCallDto> findByCoordinates(CoordinatesDto coordinates) {
    String countryName = locationService.getCountryName(coordinates, Locale.ENGLISH);
    return findByCountry(countryName);
  }
}
