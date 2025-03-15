package com.example.CourseWork_Server.repository;

import com.example.CourseWork_Server.model.QuickMessageBox;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuickMessageBoxRepository extends JpaRepository<QuickMessageBox, UUID> {
  /**
   * Retrieves a {@link QuickMessageBox} associated with a specific user.
   *
   * @param userId the unique identifier (UUID) of the user.
   * @return an {@link Optional} containing the {@link QuickMessageBox} if found, otherwise empty.
   */
  @Query("SELECT m FROM QuickMessageBox m WHERE m.user.id = :userId")
  Optional<QuickMessageBox> findByUserId(UUID userId);
}
