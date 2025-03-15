package com.example.CourseWork_Server.security.service;

import com.example.CourseWork_Server.enums.TokenType;
import com.example.CourseWork_Server.model.User;
import io.jsonwebtoken.Claims;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {

  /**
   * Extracts the claims from a JWT token.
   *
   * @param token the JWT token
   * @param type the type of the token (e.g., ACCESS_TOKEN, REFRESH_TOKEN)
   * @param claimsResolver a function to extract a specific claim from the JWT
   * @param <T> the type of the claim
   * @return the extracted claim
   */
  <T> T extractClaims(String token, TokenType type, Function<Claims, T> claimsResolver);

  /**
   * Extracts the username (subject) from a JWT token.
   *
   * @param token the JWT token
   * @param type the type of the token (e.g., ACCESS_TOKEN, REFRESH_TOKEN)
   * @return the username (subject) from the token
   */
  String extractUsername(String token, TokenType type);

  /**
   * Generates a JWT token with the specified claims and user information.
   *
   * @param extraClaims additional claims to be included in the token
   * @param user the user for whom the token is being generated
   * @param type the type of the token (e.g., ACCESS_TOKEN, REFRESH_TOKEN)
   * @return the generated JWT token
   */
  public String generateToken(Map<String, Object> extraClaims, User user, TokenType type);

  /**
   * Generates a JWT token for a user with no additional claims.
   *
   * @param user the user for whom the token is being generated
   * @param type the type of the token (e.g., ACCESS_TOKEN, REFRESH_TOKEN)
   * @return the generated JWT token
   */
  String generateToken(User user, TokenType type);

  /**
   * Checks if a JWT token has expired.
   *
   * @param token the JWT token
   * @param type the type of the token (e.g., ACCESS_TOKEN, REFRESH_TOKEN)
   * @return true if the token is expired, false otherwise
   */
  boolean isTokenExpired(String token, TokenType type);

  /**
   * Validates a JWT token for a specific user.
   *
   * @param token the JWT token
   * @param type the type of the token (e.g., ACCESS_TOKEN, REFRESH_TOKEN)
   * @param user the user to validate the token against
   * @return true if the token is valid for the user, false otherwise
   */
  boolean isTokenValid(String token, User user, TokenType type);

  /**
   * Retrieves the duration of a specific token type in milliseconds.
   *
   * @param type the type of the token (e.g., ACCESS_TOKEN, REFRESH_TOKEN)
   * @return the duration of the token in milliseconds
   */
  Long getTokenDuration(TokenType type);
}
