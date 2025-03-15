package com.example.CourseWork_Server.security.repository;

import com.example.CourseWork_Server.model.Credential;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, UUID> {
  /**
   * Finds a credential associated with a specific user by the user's ID.
   *
   * @param userId the unique identifier of the user whose credential is being searched.
   * @return an {@link Optional} containing the {@link Credential} if found, or empty if no
   *     credential is found for the given user ID.
   */
  @Query("SELECT c FROM Credential c WHERE c.user.id = :userId")
  Optional<Credential> findByUserId(UUID userId);
}
