package com.example.CourseWork_Server.service;

import com.example.CourseWork_Server.model.User;
import com.example.CourseWork_Server.model.VisuallyImpairedUser;

public interface VisuallyImpairedUserService {
  /**
   * Creates a new visually impaired user entity based on the specified user.
   *
   * @param user the {@link User} entity to be associated with the visually impaired user; must not
   *     be null
   * @return the newly created {@link VisuallyImpairedUser} entity
   */
  VisuallyImpairedUser create(User user);

  /**
   * Retrieves a {@link VisuallyImpairedUser} by their associated user's email.
   *
   * @param email the email address of the associated user; must not be null
   * @return the corresponding {@link VisuallyImpairedUser} entity
   */
  VisuallyImpairedUser findByEmail(String email);
}
