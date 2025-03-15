package com.example.CourseWork_Server.security.service;

import com.example.CourseWork_Server.model.Credential;
import com.example.CourseWork_Server.model.User;
import com.example.CourseWork_Server.security.dto.password.PasswordDto;

public interface CredentialService {
  /**
   * Finds a credential entity associated with a specific user.
   *
   * @param user the user whose credentials are to be retrieved
   * @return the credential entity associated with the specified user
   */
  Credential findByUser(User user);

  /**
   * Finds a credential entity associated with a specific user's email.
   *
   * @param userEmail the email of the user whose credentials are to be retrieved
   * @return the credential entity associated with the specified email
   */
  Credential findByUserEmail(String userEmail);

  /**
   * Creates a new credential entity for a user.
   *
   * @param password the password to be set for the user
   * @param user the user for whom the credentials are being created
   * @return the newly created credential entity
   */
  Credential create(String password, User user);

  /**
   * Changes the password for a specific user.
   *
   * @param passwordDto the data transfer object containing the new password details
   * @param user the user whose password is to be changed
   * @return the updated credential entity
   */
  Credential changePassword(PasswordDto passwordDto, User user);

  /**
   * Changes the password for a specific user.
   *
   * @param passwordDto the data transfer object containing the new password details
   * @param userEmail the email of user whose password is to be changed
   * @return the updated credential entity
   */
  Credential changePassword(PasswordDto passwordDto, String userEmail);
}
