package com.example.CourseWork_Server.security.filter;

import com.example.CourseWork_Server.dto.general.ExceptionDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws IOException {
    try {
      filterChain.doFilter(request, response);
    } catch (Exception e) {
      handleException(response, e);
    }
  }

  private void handleException(HttpServletResponse response, Exception e) throws IOException {
    HttpStatus status =
        e instanceof JwtException ? HttpStatus.UNAUTHORIZED : HttpStatus.INTERNAL_SERVER_ERROR;

    String message = e instanceof JwtException ? "JWT EXPIRED" : "An unexpected error occurred";

    ExceptionDto errorResponse = new ExceptionDto();
    errorResponse.setMessage(message);

    response.setStatus(status.value());
    response.setContentType("application/json");
    response.getWriter().write(convertObjectToJson(errorResponse));
    response.getWriter().flush();
  }

  private String convertObjectToJson(Object object) {
    try {
      return new ObjectMapper().writeValueAsString(object);
    } catch (JsonProcessingException e) {
      return "{\"error\": \"Failed to serialize error response\"}";
    }
  }
}
