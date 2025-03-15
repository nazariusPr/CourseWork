package com.example.CourseWork_Server.security.service;

import com.example.CourseWork_Server.dto.user.EmailDto;
import com.example.CourseWork_Server.dto.user.EnhancedEmailDto;
import com.example.CourseWork_Server.security.dto.auth.AuthenticateDto;
import com.example.CourseWork_Server.security.dto.auth.GoogleAuthDto;
import com.example.CourseWork_Server.security.dto.auth.RegisterDto;
import com.example.CourseWork_Server.security.dto.password.ResetPasswordDto;
import com.example.CourseWork_Server.security.dto.token.AccessRefreshTokenDto;
import com.example.CourseWork_Server.security.dto.token.AccessTokenDto;

public interface AuthenticationService {
  /**
   * Authenticates a user using the provided credentials.
   *
   * @param authenticateDto the data transfer object containing user credentials
   * @return a {@link AccessRefreshTokenDto} containing access and refresh tokens
   */
  AccessRefreshTokenDto authenticate(AuthenticateDto authenticateDto);

  /**
   * Authenticates a user using Google OAuth.
   *
   * @param authDto the data transfer object containing Google OAuth information
   * @return a {@link AccessRefreshTokenDto} containing access and refresh tokens
   */
  AccessRefreshTokenDto googleAuth(GoogleAuthDto authDto);

  /**
   * Registers a new user in the system.
   *
   * @param registerDto the data transfer object containing registration details
   */
  void register(RegisterDto registerDto);

  /**
   * Verifies a user's email using the provided verification token.
   *
   * @param verificationCode the email verification code
   * @return a {@link AccessRefreshTokenDto} containing access and refresh tokens
   */
  AccessRefreshTokenDto verifyEmail(String verificationCode);

  /**
   * Resends the email to a user's email address.
   *
   * @param emailDto consists of the email address to which email should be sent and email type
   *     (e.g. Verification, Reset Password)
   */
  void sendCodeEmail(EnhancedEmailDto emailDto);

  /**
   * Refreshes the authentication token using the provided refresh token.
   *
   * @param refreshToken the refresh token
   * @return a {@link AccessTokenDto} containing the access token
   */
  AccessTokenDto refreshToken(String refreshToken);

  /**
   * Initiates the forgot password process for a user.
   *
   * @param emailDto the data transfer object containing the user's email address
   */
  void forgotPassword(EmailDto emailDto);

  /**
   * Resets a user's password using the provided password details.
   *
   * @param passwordDto the data transfer object containing the new password details
   */
  void resetPassword(ResetPasswordDto passwordDto);
}
