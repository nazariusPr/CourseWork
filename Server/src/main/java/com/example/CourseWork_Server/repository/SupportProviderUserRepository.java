package com.example.CourseWork_Server.repository;

import com.example.CourseWork_Server.model.SupportProviderUser;
import com.example.CourseWork_Server.model.VisuallyImpairedUser;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportProviderUserRepository extends JpaRepository<SupportProviderUser, UUID> {
  /**
   * Finds a {@link SupportProviderUser} by the associated user's email.
   *
   * @param email the email address of the associated user.
   * @return an {@link Optional} containing the found {@link SupportProviderUser}, or empty if not
   *     found.
   */
  @Query("SELECT u FROM SupportProviderUser u WHERE u.user.email = :email")
  Optional<SupportProviderUser> findByEmail(String email);

  /**
   * Finds a list of {@link SupportProviderUser} entities that are associated with a {@link
   * VisuallyImpairedUser} whose email matches the provided email.
   *
   * @param email the email address of the visually impaired user.
   * @return a list of {@link SupportProviderUser} entities associated with the given email.
   */
  @Query(
      """
              SELECT sp
              FROM SupportProviderUser sp
              JOIN sp.agreements sa
              WHERE sa.visuallyImpairedUser.user.email = :email
                AND sa.supportAgreementStatus = 'ACCEPTED'
          """)
  List<SupportProviderUser> findByVisuallyImpairedUserEmail(String email);
}
