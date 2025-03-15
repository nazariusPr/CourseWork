package com.example.CourseWork_Server.utils;

import static com.example.CourseWork_Server.constant.TextResourceCode.ENTITY_NOT_FOUND;
import static com.example.CourseWork_Server.constant.TextResourceCode.INVALID_TOKEN;

import com.example.CourseWork_Server.exception.exceptions.BadRequestException;
import com.example.CourseWork_Server.exception.exceptions.InvalidTokenException;
import jakarta.persistence.EntityNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ExceptionUtils {

  private final MessageSource messageSource;

  /**
   * Creates and returns a localized exception to the specified type.
   *
   * <p>Retrieves a localized message using the provided exception code, formats it with arguments,
   * and constructs an instance of the given {@link RuntimeException} subclass.
   *
   * <p><b>Note:</b> All exceptions in this application should be thrown using this method.
   *
   * @param exceptionCode the key for retrieving the localized error message.
   * @param exceptionClass the exception class to instantiate (must extend {@link
   *     RuntimeException}).
   * @param args optional arguments for formatting the message.
   * @return a localized exception instance.
   * @throws RuntimeException if the exception cannot be instantiated.
   */
  public RuntimeException createLocalizedException(
      String exceptionCode, Class<? extends RuntimeException> exceptionClass, Object... args) {
    Locale locale = LocaleContextHolder.getLocale();
    String rawMessage = messageSource.getMessage(exceptionCode, null, locale);
    String formattedMessage = String.format(rawMessage, args);

    try {
      return exceptionClass.getDeclaredConstructor(String.class).newInstance(formattedMessage);
    } catch (InstantiationException
        | IllegalAccessException
        | NoSuchMethodException
        | InvocationTargetException e) {
      throw new RuntimeException("Error while creating and throwing exception", e);
    }
  }

  public EntityNotFoundException entityNotFoundException(Object... args) {
    return (EntityNotFoundException)
        createLocalizedException(ENTITY_NOT_FOUND, EntityNotFoundException.class, args);
  }

  public InvalidTokenException invalidTokenException(Object... args) {
    return (InvalidTokenException)
        createLocalizedException(INVALID_TOKEN, InvalidTokenException.class, args);
  }

  public BadRequestException badRequestException(String exceptionCode, Object... args) {
    return (BadRequestException)
        createLocalizedException(exceptionCode, BadRequestException.class, args);
  }

  public IllegalArgumentException illegalArgumentException(String exceptionCode, Object... args) {
    return (IllegalArgumentException)
        createLocalizedException(exceptionCode, IllegalArgumentException.class, args);
  }

  public BadCredentialsException badCredentialsException(String exceptionCode, Object... args) {
    return (BadCredentialsException)
        createLocalizedException(exceptionCode, BadCredentialsException.class, args);
  }
}
