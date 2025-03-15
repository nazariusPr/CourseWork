package com.example.CourseWork_Server.controller;

import static com.example.CourseWork_Server.constant.AppConstants.QUICK_MESSAGE_LINK;

import com.example.CourseWork_Server.dto.quickmessage.QuickMessageDetailDto;
import com.example.CourseWork_Server.dto.quickmessage.QuickMessageDto;
import com.example.CourseWork_Server.service.QuickMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(QUICK_MESSAGE_LINK)
public class QuickMessageController {
  private final QuickMessageService service;

  @Operation(
      summary = "Send a quick message",
      description = "Sends a quick message from the logged-in user to the recipients.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Quick message sent successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid message details"),
    @ApiResponse(responseCode = "401", description = "Unauthorized - User is not authenticated"),
    @ApiResponse(
        responseCode = "403",
        description = "Forbidden - User does not have the required role")
  })
  @PreAuthorize("hasRole('VISUALLY_IMPAIRED_USER')")
  @PostMapping("/send")
  public ResponseEntity<QuickMessageDetailDto> sendQuickMessage(
      @Valid @RequestBody QuickMessageDto quickMessageDto, Principal principal) {
    log.info("**/ Sending quick message from {}", principal.getName());
    QuickMessageDetailDto detailDto =
        service.sendQuickMessage(principal.getName(), quickMessageDto);
    return ResponseEntity.ok(detailDto);
  }
}
