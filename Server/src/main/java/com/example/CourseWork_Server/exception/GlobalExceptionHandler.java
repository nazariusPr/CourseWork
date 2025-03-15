package com.example.CourseWork_Server.exception;

import com.example.CourseWork_Server.dto.general.ExceptionDto;
import com.example.CourseWork_Server.exception.exceptions.BadRequestException;
import com.example.CourseWork_Server.exception.exceptions.InvalidTokenException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@AllArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {
  private ErrorAttributes errorAttributes;

  @ExceptionHandler({
    ValidationException.class,
    BadRequestException.class,
    IllegalArgumentException.class,
    ConstraintViolationException.class,
    MethodArgumentNotValidException.class,
    DataIntegrityViolationException.class
  })
  public final ResponseEntity<?> handleBadRequestException(WebRequest request) {
    ExceptionDto exceptionDto = new ExceptionDto(getErrorAttributes(request));

    log.warn(exceptionDto.getMessage(), exceptionDto);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionDto);
  }

  @ExceptionHandler({
    SecurityException.class,
    InvalidTokenException.class,
    BadCredentialsException.class
  })
  public final ResponseEntity<?> handleForbiddenException(WebRequest request) {
    ExceptionDto exceptionDto = new ExceptionDto(getErrorAttributes(request));

    log.warn(exceptionDto.getMessage(), exceptionDto);
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exceptionDto);
  }

  @ExceptionHandler({EntityNotFoundException.class})
  public final ResponseEntity<?> handleNotFoundException(WebRequest request) {
    ExceptionDto exceptionDto = new ExceptionDto(getErrorAttributes(request));

    log.warn(exceptionDto.getMessage(), exceptionDto);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionDto);
  }

  private Map<String, Object> getErrorAttributes(WebRequest webRequest) {
    return new HashMap<>(
        errorAttributes.getErrorAttributes(
            webRequest, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE)));
  }
}
