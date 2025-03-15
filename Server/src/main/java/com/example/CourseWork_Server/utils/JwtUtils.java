package com.example.CourseWork_Server.utils;

import static com.example.CourseWork_Server.constant.TextResourceCode.UNKNOWN_TOKEN_TYPE;
import static com.example.CourseWork_Server.utils.ValidationUtils.validateObject;

import com.example.CourseWork_Server.enums.TokenType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

  @Value("${jwt.secrets.access-token}")
  private String accessTokenSecret;

  @Value("${jwt.secrets.refresh-token}")
  private String refreshTokenSecret;

  @Value("${jwt.durations.access-token}")
  private Long accessTokenDuration;

  @Value("${jwt.durations.refresh-token}")
  private Long refreshTokenDuration;

  /**
   * Retrieves the appropriate secret key for the given token type.
   *
   * @param type the token type
   * @return the secret key for the token type
   * @throws IllegalArgumentException if the token type is null or unknown
   */
  public String getTokenSecretKey(TokenType type) {
    validateObject(type, "TYPE");

    return switch (type) {
      case ACCESS_TOKEN -> accessTokenSecret;
      case REFRESH_TOKEN -> refreshTokenSecret;
      default -> throw new IllegalArgumentException(UNKNOWN_TOKEN_TYPE);
    };
  }

  /**
   * Retrieves the duration for the given token type in milliseconds.
   *
   * @param type the token type
   * @return the duration in milliseconds for the token type
   * @throws IllegalArgumentException if the token type is null or unknown
   */
  public Long getTokenDuration(TokenType type) {
    validateObject(type, "TYPE");

    return switch (type) {
      case ACCESS_TOKEN -> accessTokenDuration;
      case REFRESH_TOKEN -> refreshTokenDuration;
      default -> throw new IllegalArgumentException(UNKNOWN_TOKEN_TYPE);
    };
  }
}
