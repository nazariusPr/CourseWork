package com.example.CourseWork_Server.repository;

import com.example.CourseWork_Server.enums.AccountStatus;
import com.example.CourseWork_Server.model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  /**
   * Finds a user by their email address.
   *
   * @param email the email address of the user to be searched.
   * @return an {@link Optional} containing the user if found, or empty if no user with the given
   *     email exists.
   */
  @Query("SELECT u FROM User u WHERE u.email = :email")
  Optional<User> findByEmail(String email);

  /**
   * Finds users by their account status.
   *
   * @param accountStatus the account status type to filter users.
   * @return a {@link List} of users who have the given account status.
   */
  @Query("SELECT u FROM User u WHERE u.accountStatus = :accountStatus")
  List<User> findByStatus(AccountStatus accountStatus);

  /**
   * Checks if a user exists by their email address.
   *
   * @param email the email address of the user to be checked.
   * @return {@code true} if a user with the given email exists, {@code false} otherwise.
   */
  @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email")
  boolean existsByEmail(String email);
}
