package com.example.CourseWork_Server.resolver;

import com.example.CourseWork_Server.service.LanguageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

@Component
public class CustomLocaleResolver implements LocaleResolver {
  private final List<String> languageCodes;

  public CustomLocaleResolver(LanguageService languageService) {
    languageCodes = languageService.findAllLanguageCodes();
  }

  @Override
  @NonNull
  public Locale resolveLocale(@NonNull HttpServletRequest request) {
    LocaleContextHolder.setDefaultLocale(Locale.ENGLISH);
    String acceptLanguage = request.getHeader("Accept-Language");

    if (acceptLanguage != null && !acceptLanguage.isEmpty()) {
      String languageTag = acceptLanguage.split(",")[0].trim();

      if (languageCodes.contains(languageTag)) {
        return Locale.forLanguageTag(languageTag);
      }
    }

    return Locale.ENGLISH;
  }

  @Override
  public void setLocale(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull Locale locale) {
    LocaleContextHolder.setLocale(locale);
  }
}
