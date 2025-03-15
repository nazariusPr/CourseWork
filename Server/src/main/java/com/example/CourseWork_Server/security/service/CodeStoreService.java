package com.example.CourseWork_Server.security.service;

import com.example.CourseWork_Server.enums.CodeType;

public interface CodeStoreService {

  /**
   * Sets a code for a given user and associates it with a specific code type. The code will also
   * have an expiration time defined by the given duration.
   *
   * @param email The user's email for whom the code is being set.
   * @param code The code to be stored.
   * @param type The type of the code (e.g., verification or password reset).
   * @param duration The duration (in milliseconds) for which the code is valid.
   * @return The status or result of setting the code.
   */
  String setCode(String email, String code, CodeType type, Long duration);

  /**
   * Retrieves a user's email based on key.
   *
   * @param key The key (could be a code or identifier) used to find the user.
   * @return The user associated with the provided key.
   */
  String getEmailByKey(String key);

  /**
   * Retrieves a user by the provided code and code type. If the code is valid, the corresponding
   * user's information is returned.
   *
   * @param code The code to search for.
   * @param type The type of the code (e.g., verification or password reset).
   * @return The identifier or details of the user associated with the provided code.
   */
  String getEmailByCode(String code, CodeType type);

  /**
   * Deletes the stored code for the provided code and code type. This could be used when the code
   * has expired or been successfully used.
   *
   * @param code The code to be deleted.
   * @param type The type of the code (e.g., verification or password reset).
   */
  void deleteCode(String code, CodeType type);
}
