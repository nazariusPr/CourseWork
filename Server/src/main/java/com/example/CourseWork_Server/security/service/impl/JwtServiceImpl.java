package com.example.CourseWork_Server.security.service.impl;

import com.example.CourseWork_Server.enums.TokenType;
import com.example.CourseWork_Server.model.User;
import com.example.CourseWork_Server.security.service.JwtService;
import com.example.CourseWork_Server.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtServiceImpl implements JwtService {
  private final JwtUtils jwtUtils;

  /** {@inheritDoc} */
  @Override
  public <T> T extractClaims(String token, TokenType type, Function<Claims, T> claimsResolver) {
    Claims claims = this.extractAllClaims(token, type);
    return claimsResolver.apply(claims);
  }

  /** {@inheritDoc} */
  @Override
  public String extractUsername(String token, TokenType type) {
    return this.extractClaims(token, type, Claims::getSubject);
  }

  /** {@inheritDoc} */
  @Override
  public String generateToken(Map<String, Object> extraClaims, User user, TokenType type) {
    return Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(user.getEmail())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + jwtUtils.getTokenDuration(type)))
        .signWith(getSignInKey(type), SignatureAlgorithm.HS256)
        .compact();
  }

  /** {@inheritDoc} */
  @Override
  public String generateToken(User user, TokenType type) {
    return generateToken(new HashMap<>(), user, type);
  }

  /** {@inheritDoc} */
  @Override
  public boolean isTokenExpired(String token, TokenType type) {
    return extractExpiration(token, type).before(new Date());
  }

  /** {@inheritDoc} */
  @Override
  public boolean isTokenValid(String token, User user, TokenType type) {
    String email = extractUsername(token, type);
    return email.equals(user.getEmail()) && !isTokenExpired(token, type);
  }

  /** {@inheritDoc} */
  @Override
  public Long getTokenDuration(TokenType type) {
    return jwtUtils.getTokenDuration(type);
  }

  private Claims extractAllClaims(String token, TokenType type) {
    return Jwts.parserBuilder()
        .setSigningKey(getSignInKey(type))
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSignInKey(TokenType type) {
    byte[] keyBytes = Decoders.BASE64.decode(jwtUtils.getTokenSecretKey(type));
    return Keys.hmacShaKeyFor(keyBytes);
  }

  private Date extractExpiration(String token, TokenType type) {
    return extractClaims(token, type, Claims::getExpiration);
  }
}
