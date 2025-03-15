package com.example.CourseWork_Server.security.service.impl;

import static com.example.CourseWork_Server.constant.TextResourceCode.BAD_CREDENTIALS;
import static com.example.CourseWork_Server.constant.TextResourceCode.HAS_TO_SPECIFY_USER_TYPE;
import static com.example.CourseWork_Server.constant.TextResourceCode.INVALID_EMAIL_TYPE;
import static com.example.CourseWork_Server.constant.TextResourceCode.UNKNOWN_USER_TYPE;

import com.example.CourseWork_Server.dto.user.EmailDto;
import com.example.CourseWork_Server.dto.user.EnhancedEmailDto;
import com.example.CourseWork_Server.enums.AccountStatus;
import com.example.CourseWork_Server.enums.AuthMethod;
import com.example.CourseWork_Server.enums.CodeType;
import com.example.CourseWork_Server.enums.EmailType;
import com.example.CourseWork_Server.enums.TokenType;
import com.example.CourseWork_Server.enums.UserType;
import com.example.CourseWork_Server.model.User;
import com.example.CourseWork_Server.security.dto.auth.AuthenticateDto;
import com.example.CourseWork_Server.security.dto.auth.GoogleAuthDto;
import com.example.CourseWork_Server.security.dto.auth.RegisterDto;
import com.example.CourseWork_Server.security.dto.password.ResetPasswordDto;
import com.example.CourseWork_Server.security.dto.token.AccessRefreshTokenDto;
import com.example.CourseWork_Server.security.dto.token.AccessTokenDto;
import com.example.CourseWork_Server.security.service.AuthenticationService;
import com.example.CourseWork_Server.security.service.CodeStoreService;
import com.example.CourseWork_Server.security.service.CredentialService;
import com.example.CourseWork_Server.security.service.JwtService;
import com.example.CourseWork_Server.service.EmailService;
import com.example.CourseWork_Server.service.SupportProviderUserService;
import com.example.CourseWork_Server.service.UserService;
import com.example.CourseWork_Server.service.VisuallyImpairedUserService;
import com.example.CourseWork_Server.utils.CodeUtils;
import com.example.CourseWork_Server.utils.ExceptionUtils;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.function.BiConsumer;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
  private final UserService userService;
  private final SupportProviderUserService providerUserService;
  private final VisuallyImpairedUserService impairedUserService;
  private final EmailService emailService;
  private final CredentialService credentialService;
  private final CodeStoreService codeStoreService;
  private final JwtService jwtService;
  private final AuthenticationManager authManager;
  private final GoogleIdTokenVerifier googleVerifier;
  private final CodeUtils codeUtils;
  private final ExceptionUtils exceptionUtils;

  /** {@inheritDoc} */
  @Override
  public AccessRefreshTokenDto authenticate(AuthenticateDto authenticateDto) {
    String email = authenticateDto.getEmail();
    Authentication authentication =
        new UsernamePasswordAuthenticationToken(email, authenticateDto.getPassword());
    authManager.authenticate(authentication);

    User user = userService.findByEmail(email);
    return grantTokens(user);
  }

  /** {@inheritDoc} */
  @Override
  public AccessRefreshTokenDto googleAuth(GoogleAuthDto authDto) {
    String token = authDto.getToken();
    User user = processGoogleAuth(token, authDto.getUserType());

    return grantTokens(user);
  }

  /** {@inheritDoc} */
  @Override
  public void register(RegisterDto registerDto) {
    createSimpleUser(registerDto);

    EnhancedEmailDto emailDto = new EnhancedEmailDto();
    emailDto.setEmail(registerDto.getEmail());
    emailDto.setType(EmailType.VERIFICATION_EMAIL);

    sendCodeEmail(emailDto);
  }

  /** {@inheritDoc} */
  @Override
  public AccessRefreshTokenDto verifyEmail(String verificationCode) {
    CodeType type = CodeType.VERIFICATION_CODE;

    String email = codeStoreService.getEmailByCode(verificationCode, type);
    User user = userService.changeAccountStatus(email, AccountStatus.VERIFIED);

    codeStoreService.deleteCode(verificationCode, type);

    return grantTokens(user);
  }

  /** {@inheritDoc} */
  @Override
  public void sendCodeEmail(EnhancedEmailDto emailDto) {
    EmailType type = emailDto.getType();
    String email = emailDto.getEmail();
    BiConsumer<String, String> sendEmailMethod;
    CodeType codeType;

    switch (type) {
      case VERIFICATION_EMAIL -> {
        codeType = CodeType.VERIFICATION_CODE;
        sendEmailMethod = emailService::sendVerificationEmail;
      }
      case FORGOT_PASSWORD_EMAIL -> {
        codeType = CodeType.FORGOT_PASSWORD_CODE;
        sendEmailMethod = emailService::sendForgotPasswordEmail;
      }

      default -> throw exceptionUtils.illegalArgumentException(INVALID_EMAIL_TYPE, type);
    }

    String code = codeUtils.generateRandomNumericCode(6);
    sendEmailMethod.accept(email, code);

    Long duration = codeUtils.getCodeDuration(codeType);
    codeStoreService.setCode(email, code, codeType, duration);
  }

  /** {@inheritDoc} */
  @Override
  public AccessTokenDto refreshToken(String refreshToken) {
    TokenType type = TokenType.REFRESH_TOKEN;

    if (jwtService.isTokenExpired(refreshToken, type)) {
      throw exceptionUtils.invalidTokenException(type);
    }

    User user = getUserFromToken(refreshToken, type);
    Map<String, Object> claims = Map.of("type", user.getUserType());
    String accessToken = jwtService.generateToken(claims, user, TokenType.ACCESS_TOKEN);

    return new AccessTokenDto(accessToken);
  }

  /** {@inheritDoc} */
  @Override
  public void forgotPassword(EmailDto emailDto) {
    EnhancedEmailDto enhancedEmailDto = new EnhancedEmailDto();
    enhancedEmailDto.setEmail(emailDto.getEmail());
    enhancedEmailDto.setType(EmailType.FORGOT_PASSWORD_EMAIL);

    sendCodeEmail(enhancedEmailDto);
  }

  /** {@inheritDoc} */
  @Override
  public void resetPassword(ResetPasswordDto passwordDto) {
    CodeType type = CodeType.FORGOT_PASSWORD_CODE;
    String codeReset = passwordDto.getResetCode();
    String email = codeStoreService.getEmailByCode(codeReset, type);

    credentialService.changePassword(passwordDto, email);

    codeStoreService.deleteCode(codeReset, type);
  }

  private User getUserFromToken(String token, TokenType tokenType) {
    String email = jwtService.extractUsername(token, tokenType);
    return userService.findByEmail(email);
  }

  private void createSimpleUser(RegisterDto registerDto) {
    User user = userService.create(registerDto, AuthMethod.EMAIL, AccountStatus.NOT_VERIFIED);
    credentialService.create(registerDto.getPassword(), user);

    createDerivedUser(user, registerDto.getUserType());
  }

  private void createDerivedUser(User user, UserType type) {
    switch (type) {
      case VISUALLY_IMPAIRED_USER -> impairedUserService.create(user);
      case SUPPORT_PROVIDER_USER -> providerUserService.create(user);
      default -> throw exceptionUtils.illegalArgumentException(UNKNOWN_USER_TYPE, type);
    }
  }

  private AccessRefreshTokenDto grantTokens(User user) {
    String refreshToken = jwtService.generateToken(user, TokenType.REFRESH_TOKEN);
    Map<String, Object> claims = Map.of("type", user.getUserType());

    String accessToken = jwtService.generateToken(claims, user, TokenType.ACCESS_TOKEN);

    AccessRefreshTokenDto tokenDto = new AccessRefreshTokenDto();
    tokenDto.setAccessToken(accessToken);
    tokenDto.setRefreshToken(refreshToken);

    return tokenDto;
  }

  private GoogleIdToken.Payload getGooglePayload(String token) {
    if (token == null || token.isEmpty()) {
      throw exceptionUtils.invalidTokenException("Google Token");
    }
    try {
      GoogleIdToken googleIdToken = googleVerifier.verify(token);
      if (googleIdToken == null) {
        throw exceptionUtils.invalidTokenException("Google Token");
      }

      return googleIdToken.getPayload();

    } catch (GeneralSecurityException | IOException ex) {
      throw exceptionUtils.invalidTokenException("Google Token");
    }
  }

  private User processGoogleAuth(String token, UserType userType) {
    User user;
    GoogleIdToken.Payload payload = getGooglePayload(token);
    String email = payload.getEmail();

    if (userService.doesUserExist(email)) {
      user = userService.findByEmail(email);
      if (user.getAuthMethod() != AuthMethod.GOOGLE) {
        throw exceptionUtils.badCredentialsException(BAD_CREDENTIALS);
      }

    } else {
      if (userType == null) {
        throw exceptionUtils.badRequestException(HAS_TO_SPECIFY_USER_TYPE);
      }

      RegisterDto registerDto = RegisterDto.builder().email(payload.getEmail()).build();
      user = userService.create(registerDto, AuthMethod.GOOGLE, AccountStatus.VERIFIED);

      createDerivedUser(user, userType);
    }
    return user;
  }
}
