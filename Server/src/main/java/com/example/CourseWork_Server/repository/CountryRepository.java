package com.example.CourseWork_Server.repository;

import com.example.CourseWork_Server.model.Country;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, UUID> {
  /**
   * Retrieves a country entity by its name.
   *
   * @param name the name of the country to search for
   * @return an {@link Optional} containing the matching {@link Country} if found, or empty
   *     otherwise
   */
  @Query("SELECT c FROM Country c WHERE c.name = :name")
  Optional<Country> findByName(String name);
}
