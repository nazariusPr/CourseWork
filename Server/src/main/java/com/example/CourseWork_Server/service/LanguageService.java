package com.example.CourseWork_Server.service;

import java.util.List;

public interface LanguageService {
  /**
   * Method, that returns codes of all languages.
   *
   * @return {@link List} of language code strings.
   */
  List<String> findAllLanguageCodes();
}
