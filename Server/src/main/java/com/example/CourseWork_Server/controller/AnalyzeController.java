package com.example.CourseWork_Server.controller;

import static com.example.CourseWork_Server.constant.AppConstants.ANALYZE_LINK;

import com.example.CourseWork_Server.dto.general.Base64;
import com.example.CourseWork_Server.dto.general.MessageDto;
import com.example.CourseWork_Server.service.AnalyzeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(ANALYZE_LINK)
public class AnalyzeController {
  private final AnalyzeService analyzeService;

  @Operation(
      summary = "Analyze an image (Multipart)",
      description =
          "Uploads and analyzes an image file. Only image formats are supported (JPEG, PNG, etc.).")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Image analyzed successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid file format or missing file"),
  })
  @PostMapping(value = "/multipart", consumes = "multipart/form-data")
  public ResponseEntity<MessageDto> read(@RequestPart MultipartFile multipartFile) {
    log.info("**/ Analyze");
    return ResponseEntity.ok(analyzeService.analyze(multipartFile));
  }

  @Operation(
      summary = "Analyze an image (Base64)",
      description = "Uploads and analyzes an image encoded in Base64 format.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Image analyzed successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid Base64 format or missing data"),
  })
  @PostMapping(value = "/base64")
  public ResponseEntity<MessageDto> analyzeBase64(@Valid @RequestBody Base64 base64) {
    log.info("**/ Analyze");
    return ResponseEntity.ok(analyzeService.analyze(base64.getBase64()));
  }
}
