package com.example.CourseWork_Server.repository;

import com.example.CourseWork_Server.model.VisuallyImpairedUser;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VisuallyImpairedUserRepository extends JpaRepository<VisuallyImpairedUser, UUID> {
  /**
   * Finds a {@link VisuallyImpairedUser} by the associated user's email.
   *
   * @param email the email address of the associated user.
   * @return an {@link Optional} containing the found {@link VisuallyImpairedUser}, or empty if not
   *     found.
   */
  @Query("SELECT u FROM VisuallyImpairedUser u WHERE u.user.email = :email")
  Optional<VisuallyImpairedUser> findByEmail(String email);
}
