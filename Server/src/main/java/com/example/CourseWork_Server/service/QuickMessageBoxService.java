package com.example.CourseWork_Server.service;

import com.example.CourseWork_Server.model.QuickMessageBox;
import com.example.CourseWork_Server.model.User;
import java.util.UUID;

public interface QuickMessageBoxService {
  /**
   * Retrieves a {@link QuickMessageBox} by its unique identifier.
   *
   * @param id the unique identifier (UUID) of the message box.
   * @return the {@link QuickMessageBox} if found.
   */
  QuickMessageBox findById(UUID id);

  /**
   * Retrieves a {@link QuickMessageBox} associated with a specific user.
   *
   * @param userId the unique identifier (UUID) of the user.
   * @return the {@link QuickMessageBox} if found.
   */
  QuickMessageBox findByUserId(UUID userId);

  /**
   * Creates a new message box for the given user.
   *
   * <p>This method is responsible for creating a message box associated with the user. It ensures
   * that each user can have only one message box. If the user already has a message box,
   * appropriate handling or exception should be done.
   *
   * @param user The user for whom the message box will be created.
   * @return The newly created {@link QuickMessageBox} associated with the user.
   */
  QuickMessageBox create(User user);
}
