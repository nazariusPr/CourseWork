package com.example.CourseWork_Server.service;

import com.example.CourseWork_Server.enums.AccountStatus;
import com.example.CourseWork_Server.enums.AuthMethod;
import com.example.CourseWork_Server.model.User;
import com.example.CourseWork_Server.security.dto.auth.RegisterDto;
import java.util.List;

public interface UserService {
  /**
   * Finds a user by their email address.
   *
   * @param email the email address of the user
   * @return the user entity corresponding to the specified email
   */
  User findByEmail(String email);

  /**
   * Retrieves a list of users with the specified account status.
   *
   * @param status the account status of the users to retrieve
   * @return a list of users with the specified account status
   */
  List<User> findByStatus(AccountStatus status);

  /**
   * Persists the given user entity to the database.
   *
   * @param user the user entity to be saved; must not be null
   * @return the saved user entity, including any changes made during the save process (e.g.,
   *     generated ID or timestamps)
   */
  User save(User user);

  /**
   * Creates a new user entity with the specified registration details, authentication method and
   * account status.
   *
   * @param registerDto the registration details of the user
   * @param authMethod the authentication method to be associated with the user
   * @param accountStatus the initial account status of the user
   * @return the newly created user entity
   */
  User create(RegisterDto registerDto, AuthMethod authMethod, AccountStatus accountStatus);

  /**
   * Creates a new user entity based on the provided registration details, authentication method,
   * account status, and an option to persist the entity to the database.
   *
   * @param registerDto the registration details containing user information such as email and
   *     password
   * @param authMethod the method of authentication to be associated with the user (e.g., EMAIL,
   *     SOCIAL)
   * @param accountStatus the initial account status of the user (e.g., ACTIVE, NOT_VERIFIED)
   * @param persist whether to save the newly created user entity to the database
   * @return the newly created user entity
   */
  User create(
      RegisterDto registerDto, AuthMethod authMethod, AccountStatus accountStatus, boolean persist);

  /**
   * Deletes a user from the system identified by their email address.
   *
   * @param email the email address of the user to be deleted
   */
  void delete(String email);

  /**
   * Changes the account status of a user identified by their email.
   *
   * @param email the email address of the user
   * @param status the new account status to be applied
   * @return the updated user entity with the changed account status
   */
  User changeAccountStatus(String email, AccountStatus status);

  /**
   * Updates the account status of the specified user.
   *
   * @param user the user whose account status is to be updated
   * @param status the new account status to be applied
   * @return the updated user entity with the changed account status
   */
  User changeAccountStatus(User user, AccountStatus status);

  /**
   * Checks if a user exists in the system by their email address.
   *
   * @param email the email address of the user
   * @return {@code true} if the user exists, {@code false} otherwise
   */
  boolean doesUserExist(String email);
}
