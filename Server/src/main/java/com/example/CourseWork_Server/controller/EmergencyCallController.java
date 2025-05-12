package com.example.CourseWork_Server.controller;

import static com.example.CourseWork_Server.constant.AppConstants.EMERGENCY_CALL_LINK;

import com.example.CourseWork_Server.dto.emergency.EmergencyCallDto;
import com.example.CourseWork_Server.dto.location.CoordinatesDto;
import com.example.CourseWork_Server.service.EmergencyCallService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
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
@RequestMapping(EMERGENCY_CALL_LINK)
public class EmergencyCallController {
  private final EmergencyCallService emergencyCallService;

  @Operation(
      summary = "Get emergency numbers by coordinates",
      description =
          "Returns a list of emergency contact numbers based on the user's geographical location.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved emergency numbers"),
        @ApiResponse(responseCode = "400", description = "Invalid coordinates supplied"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @GetMapping
  public ResponseEntity<List<EmergencyCallDto>> read(
      @RequestParam double lat, @RequestParam double lon) {
    log.info("**/ Get emergency numbers based on user's location");

    List<EmergencyCallDto> emergencyNumbers =
        emergencyCallService.findByCoordinates(new CoordinatesDto(lat, lon));
    return ResponseEntity.ok(emergencyNumbers);
  }
}
