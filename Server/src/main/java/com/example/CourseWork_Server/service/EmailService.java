package com.example.CourseWork_Server.service;

public interface EmailService {
  /**
   * Sends a verification email to the specified recipient.
   *
   * @param to the recipient's email address
   * @param verificationCode the Code for verifying the recipient's email
   */
  void sendVerificationEmail(String to, String verificationCode);

  /**
   * Sends a forgot password email to the specified recipient.
   *
   * @param to the recipient's email address
   * @param forgotPasswordCode the URL for resetting the recipient's password
   */
  void sendForgotPasswordEmail(String to, String forgotPasswordCode);

  /**
   * Sends a reminder verification email to the specified recipient. This email serves as a
   * follow-up to remind the recipient to verify their email address.
   *
   * @param to the recipient's email address
   */
  void sendReminderVerificationEmail(String to);
}
