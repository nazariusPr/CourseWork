package com.example.CourseWork_Server.security.filter;

import static com.example.CourseWork_Server.utils.SecurityUtils.getUserAuthorities;

import com.example.CourseWork_Server.enums.TokenType;
import com.example.CourseWork_Server.model.User;
import com.example.CourseWork_Server.security.service.JwtService;
import com.example.CourseWork_Server.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final UserService userService;
  private final JwtService jwtService;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");
    final String accessToken;
    final String email;

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }
    accessToken = authHeader.substring(7);
    email = jwtService.extractUsername(accessToken, TokenType.ACCESS_TOKEN);

    if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      User user = userService.findByEmail(email);

      if (jwtService.isTokenValid(accessToken, user, TokenType.ACCESS_TOKEN)) {
        Authentication authentication =
            new UsernamePasswordAuthenticationToken(email, "", getUserAuthorities(user));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    filterChain.doFilter(request, response);
  }
}
