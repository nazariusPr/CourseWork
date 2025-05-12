package com.example.CourseWork_Server.service.impl;

import static com.example.CourseWork_Server.constant.AppConstants.GET_LOCATION_URL;

import com.example.CourseWork_Server.dto.general.MessageDto;
import com.example.CourseWork_Server.dto.location.AddressDto;
import com.example.CourseWork_Server.dto.location.CoordinatesDto;
import com.example.CourseWork_Server.dto.location.LocationDto;
import com.example.CourseWork_Server.service.LocationService;
import com.example.CourseWork_Server.utils.RequestUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {
  private final RequestUtils requestUtils;
  private final MessageSource messageSource;

  /** {@inheritDoc} */
  @Override
  public LocationDto getLocationDto(CoordinatesDto coordinatesDto, Locale locale) {
    String url =
        String.format(
            GET_LOCATION_URL,
            coordinatesDto.getLatitude(),
            coordinatesDto.getLongitude(),
            locale.getLanguage());
    return requestUtils.getRequest(url, LocationDto.class);
  }

  /** {@inheritDoc} */
  @Override
  public String getCountryName(CoordinatesDto coordinatesDto, Locale locale) {
    AddressDto addressDto = getLocationDto(coordinatesDto, locale).getAddress();
    return addressDto.getCountry().trim();
  }

  /** {@inheritDoc} */
  @Override
  @Cacheable("location")
  public MessageDto getLocationMessage(CoordinatesDto coordinatesDto, Locale locale) {
    LocationDto location = getLocationDto(coordinatesDto, locale);
    AddressDto address = location.getAddress();
    List<String> parts = new ArrayList<>();

    parts.add(messageSource.getMessage("location.beginning", null, locale));
    addLocationPart(parts, null, address.getRoad(), locale);
    addLocationPart(parts, null, address.getHouseNumber(), locale);

    if (StringUtils.isNotEmpty(address.getCity())) {
      addLocationPart(parts, "location.city", address.getCity(), locale);
    } else if (StringUtils.isNotEmpty(address.getVillage())) {
      addLocationPart(parts, "location.village", address.getVillage(), locale);
    }

    addLocationPart(parts, null, address.getDistrict(), locale);
    addLocationPart(parts, null, address.getState(), locale);
    addLocationPart(parts, null, address.getCountry(), locale);

    return new MessageDto(String.join(" ", parts));
  }

  private void addLocationPart(List<String> parts, String messageKey, String value, Locale locale) {
    if (StringUtils.isNotEmpty(value)) {
      parts.add(
          (messageKey != null ? messageSource.getMessage(messageKey, null, locale) + " " : "")
              + value);
    }
  }
}
