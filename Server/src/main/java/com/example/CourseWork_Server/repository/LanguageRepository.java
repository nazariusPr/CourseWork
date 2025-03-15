package com.example.CourseWork_Server.repository;

import com.example.CourseWork_Server.model.Language;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, UUID> {
  /**
   * method, that returns codes of all {@link Language}s.
   *
   * @return {@link List} of language code strings.
   */
  @Query("SELECT code FROM Language")
  List<String> findAllLanguageCodes();
}
