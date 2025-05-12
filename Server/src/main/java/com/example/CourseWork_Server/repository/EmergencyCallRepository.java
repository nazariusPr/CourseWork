package com.example.CourseWork_Server.repository;

import com.example.CourseWork_Server.model.EmergencyCall;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmergencyCallRepository extends JpaRepository<EmergencyCall, UUID> {
  /**
   * Retrieves a list of {@link EmergencyCall} entities associated with a given country name.
   *
   * @param countryName the name of the country for which emergency numbers are to be retrieved.
   * @return a list of emergency numbers linked to the specified country.
   */
  @Query("SELECT e FROM EmergencyCall e WHERE e.country.name = :countryName")
  List<EmergencyCall> findByCountryName(String countryName);
}
