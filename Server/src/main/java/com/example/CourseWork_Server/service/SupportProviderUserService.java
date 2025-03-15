package com.example.CourseWork_Server.service;

import com.example.CourseWork_Server.model.SupportProviderUser;
import com.example.CourseWork_Server.model.User;
import java.util.List;

public interface SupportProviderUserService {
  /**
   * Creates a new support provider user entity based on the specified user.
   *
   * @param user the {@link User} entity to be associated with the support provider; must not be
   *     null
   * @return the newly created {@link SupportProviderUser} entity
   */
  SupportProviderUser create(User user);

  /**
   * Retrieves a {@link SupportProviderUser} by their associated user's email.
   *
   * @param email the email address of the associated user; must not be null
   * @return the corresponding {@link SupportProviderUser} entity
   */
  SupportProviderUser findByEmail(String email);

  /**
   * Retrieves a list of {@link SupportProviderUser} entities associated with a visually impaired
   * user.
   *
   * <p>This method returns all support providers linked to a visually impaired user identified by
   * their email.
   *
   * @param email the email address of the visually impaired user; must not be null.
   * @return a list of {@link SupportProviderUser} entities associated with the visually impaired
   *     user.
   */
  List<SupportProviderUser> findByVisuallyImpairedUserEmail(String email);
}
