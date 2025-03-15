package com.example.CourseWork_Server.controller;

import static com.example.CourseWork_Server.constant.AppConstants.SUPPORT_AGREEMENT_LINK;

import com.example.CourseWork_Server.dto.supportagreement.SupportAgreementDetailDto;
import com.example.CourseWork_Server.dto.supportagreement.SupportAgreementDto;
import com.example.CourseWork_Server.dto.user.EmailDto;
import com.example.CourseWork_Server.service.SupportAgreementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(SUPPORT_AGREEMENT_LINK)
public class SupportAgreementController {
  private final SupportAgreementService supportAgreementService;

  @Operation(
      summary = "Create a new support agreement",
      description =
          "Creates a new support agreement between a support provider and a visually impaired user.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Support agreement created successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid request details")
  })
  @PreAuthorize("hasRole('VISUALLY_IMPAIRED_USER')")
  @PostMapping
  public ResponseEntity<SupportAgreementDto> create(
      @Valid @RequestBody EmailDto emailDto, Principal principal) {
    log.info("**/ Create new support agreement");
    return ResponseEntity.ok(
        supportAgreementService.create(emailDto.getEmail(), principal.getName()));
  }

  @Operation(
      summary = "Retrieve support agreement details",
      description =
          "Retrieves detailed information about a specific support agreement by its unique ID.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Support agreement details retrieved successfully"),
    @ApiResponse(responseCode = "404", description = "Support agreement not found")
  })
  @PreAuthorize("@securityUtils.hasAccessToSupportAgreement(#id, authentication)")
  @GetMapping("/{id}")
  public ResponseEntity<SupportAgreementDetailDto> read(@PathVariable UUID id) {
    log.info("**/ Retrieve support agreement details");
    return ResponseEntity.ok(supportAgreementService.read(id));
  }

  @Operation(
      summary = "Update an existing support agreement",
      description = "Updates an existing support agreement based on the provided details.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Support agreement updated successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid request details")
  })
  @PreAuthorize(
      "@securityUtils.hasAccessToSupportAgreement(#id, authentication) and hasRole('SUPPORT_PROVIDER_USER')")
  @PutMapping("/{id}")
  public ResponseEntity<SupportAgreementDto> update(
      @PathVariable UUID id, @Valid @RequestBody SupportAgreementDto agreementDto) {
    log.info("**/ Update support agreement");
    return ResponseEntity.ok(supportAgreementService.update(id, agreementDto));
  }

  @Operation(
      summary = "Delete a support agreement",
      description = "Deletes a specific support agreement using its unique ID.")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Support agreement deleted successfully"),
    @ApiResponse(responseCode = "404", description = "Support agreement not found")
  })
  @PreAuthorize("@securityUtils.hasAccessToSupportAgreement(#id, authentication)")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    log.info("**/ Delete support agreement");
    supportAgreementService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
