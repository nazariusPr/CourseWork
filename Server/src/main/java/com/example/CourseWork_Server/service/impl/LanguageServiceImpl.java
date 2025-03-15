package com.example.CourseWork_Server.service.impl;

import com.example.CourseWork_Server.repository.LanguageRepository;
import com.example.CourseWork_Server.service.LanguageService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LanguageServiceImpl implements LanguageService {
  private final LanguageRepository repository;

  /** {@inheritDoc} */
  @Override
  public List<String> findAllLanguageCodes() {
    return repository.findAllLanguageCodes();
  }
}
