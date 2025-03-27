package com.example.CourseWork_Server.controller;

import static com.example.CourseWork_Server.constant.AppConstants.LOCATION_LINK;

import com.example.CourseWork_Server.dto.general.MessageDto;
import com.example.CourseWork_Server.dto.location.CoordinatesDto;
import com.example.CourseWork_Server.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(LOCATION_LINK)
public class LocationController {
  private final LocationService locationService;

  @Operation(
      summary = "Retrieve a message about the user's current location",
      description =
          "Fetches a location-based message for the user based on their latitude and longitude coordinates.",
      parameters = {
        @Parameter(
            name = "lat",
            description = "Latitude of the user's current location",
            required = true,
            example = "40.7128"),
        @Parameter(
            name = "lon",
            description = "Longitude of the user's current location",
            required = true,
            example = "-74.0060"),
        @Parameter(
            name = "locale",
            description = "Preferred language for the response",
            example = "en")
      })
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Location message retrieved successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid coordinates provided")
  })
  @GetMapping
  public ResponseEntity<MessageDto> read(
      @RequestParam double lat, @RequestParam double lon, Locale locale) {
    log.info("**/ Get user's location");
    return ResponseEntity.ok(
        locationService.getLocationMessage(new CoordinatesDto(lat, lon), locale));
  }
}
