package com.example.CourseWork_Server.security.controller;

import static com.example.CourseWork_Server.constant.AppConstants.AUTH_LINK;
import static com.example.CourseWork_Server.constant.TextResourceCode.MAIL_RESENT;
import static com.example.CourseWork_Server.constant.TextResourceCode.PASSWORD_CODE_LINK_SENT;
import static com.example.CourseWork_Server.constant.TextResourceCode.PASSWORD_RESET_SUCCESS;

import com.example.CourseWork_Server.dto.general.MessageDto;
import com.example.CourseWork_Server.dto.user.EmailDto;
import com.example.CourseWork_Server.dto.user.EnhancedEmailDto;
import com.example.CourseWork_Server.security.dto.auth.AuthenticateDto;
import com.example.CourseWork_Server.security.dto.auth.CodeDto;
import com.example.CourseWork_Server.security.dto.auth.GoogleAuthDto;
import com.example.CourseWork_Server.security.dto.auth.RegisterDto;
import com.example.CourseWork_Server.security.dto.password.ResetPasswordDto;
import com.example.CourseWork_Server.security.dto.token.AccessRefreshTokenDto;
import com.example.CourseWork_Server.security.dto.token.AccessTokenDto;
import com.example.CourseWork_Server.security.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(AUTH_LINK)
public class AuthenticationController {
  private final AuthenticationService authenticationService;

  @Operation(
      summary = "Register a new user",
      description = "Registers a new user and stores their details in the system.")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "User registered successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid registration details")
  })
  @PostMapping("/register")
  public ResponseEntity<Void> register(@Valid @RequestBody RegisterDto registerDto) {
    authenticationService.register(registerDto);
    log.info("**/ Register new user");
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Operation(summary = "Resend email", description = "Resends email to the provided email address.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Email resent successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid email address or email type")
  })
  @PostMapping("/resend-email")
  public ResponseEntity<MessageDto> resendEmail(@Valid @RequestBody EnhancedEmailDto emailDto) {
    authenticationService.sendCodeEmail(emailDto);
    log.info("**/ Resend verification email");
    return ResponseEntity.ok(new MessageDto(MAIL_RESENT));
  }

  @Operation(
      summary = "Verify email using token",
      description = "Verifies the user's email using the provided token.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "User verified successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid or expired token")
  })
  @PostMapping("/verify")
  public ResponseEntity<AccessRefreshTokenDto> verifyEmail(@Valid @RequestBody CodeDto codeDto) {
    log.info("**/ Verify user");
    return ResponseEntity.ok(authenticationService.verifyEmail(codeDto.getCode()));
  }

  @Operation(
      summary = "Authenticate user using Google OAuth2 token",
      description =
          "Authenticates the user with a Google OAuth2 token and returns access and refresh tokens.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Authentication successful"),
    @ApiResponse(
        responseCode = "400",
        description = "Invalid Google token or authentication failed")
  })
  @PostMapping("/google")
  public ResponseEntity<AccessRefreshTokenDto> googleOAuth2(
      @Valid @RequestBody GoogleAuthDto authDto) {
    log.info("**/ Google auth");
    return ResponseEntity.ok(authenticationService.googleAuth(authDto));
  }

  @Operation(
      summary = "Authenticate user",
      description =
          "Authenticates the user with their credentials and returns access and refresh tokens.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Authentication successful"),
    @ApiResponse(responseCode = "400", description = "Invalid credentials")
  })
  @PostMapping
  public ResponseEntity<AccessRefreshTokenDto> authenticate(
      @Valid @RequestBody AuthenticateDto request) {
    log.info("**/ Authenticate user");
    return ResponseEntity.ok(authenticationService.authenticate(request));
  }

  @Operation(
      summary = "Refresh authentication token",
      description = "Refreshes the user's authentication token using a valid refresh token.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid refresh token")
  })
  @GetMapping("/refresh-token")
  public ResponseEntity<AccessTokenDto> refreshToken(@RequestParam String token) {
    AccessTokenDto accessTokenDto = authenticationService.refreshToken(token);
    return ResponseEntity.ok(accessTokenDto);
  }

  @Operation(
      summary = "Initiate password reset",
      description = "Sends a password reset link to the user's email address.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Password reset link sent successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid email address")
  })
  @PostMapping("/forgot-password")
  public ResponseEntity<MessageDto> forgotPassword(@Valid @RequestBody EmailDto emailDto) {
    authenticationService.forgotPassword(emailDto);
    log.info("**/ Send forgot password email");
    return ResponseEntity.ok(new MessageDto(PASSWORD_CODE_LINK_SENT));
  }

  @Operation(
      summary = "Reset password",
      description = "Resets the user's password using the provided reset token and new password.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Password reset successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid or expired reset token"),
    @ApiResponse(responseCode = "400", description = "Password reset failed")
  })
  @PutMapping("/reset-password")
  public ResponseEntity<MessageDto> resetPassword(
      @Valid @RequestBody ResetPasswordDto resetPasswordDto) {
    authenticationService.resetPassword(resetPasswordDto);
    log.info("**/ Reset password");
    return ResponseEntity.ok(new MessageDto(PASSWORD_RESET_SUCCESS));
  }
}
