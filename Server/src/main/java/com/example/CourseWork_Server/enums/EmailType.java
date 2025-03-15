package com.example.CourseWork_Server.enums;

import lombok.Getter;

@Getter
public enum EmailType {
  VERIFICATION_EMAIL("EmailVerificationTemplate", "email.verification.subject"),
  FORGOT_PASSWORD_EMAIL("ForgotPasswordEmailTemplate", "email.forgot_password.subject"),
  REMINDER_EMAIL("ReminderEmailTemplate", "email.reminder.subject");

  private final String templateName;
  private final String subjectKey;

  EmailType(String templateName, String subjectKey) {
    this.templateName = templateName;
    this.subjectKey = subjectKey;
  }
}
