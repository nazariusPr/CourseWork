package com.example.CourseWork_Server.enums;

import static com.example.CourseWork_Server.constant.TextResourceCode.CURRENT_LOCATION_MESSAGE;
import static com.example.CourseWork_Server.constant.TextResourceCode.FEELING_UNSAFE_MESSAGE;
import static com.example.CourseWork_Server.constant.TextResourceCode.INJURED_CALL_FOR_HELP_MESSAGE;
import static com.example.CourseWork_Server.constant.TextResourceCode.LOST_MESSAGE;
import static com.example.CourseWork_Server.constant.TextResourceCode.NEED_ASSISTANCE_MESSAGE;
import static com.example.CourseWork_Server.constant.TextResourceCode.NEED_MEDICAL_ATTENTION_MESSAGE;

import lombok.Getter;

/**
 * Enum representing different types of quick messages that visually impaired users can send in the
 * application.
 */
@Getter
public enum QuickMessageType {

  // Help/Assistance Requests
  NEED_ASSISTANCE(NEED_ASSISTANCE_MESSAGE),
  FEELING_UNSAFE(FEELING_UNSAFE_MESSAGE),

  // Informational Messages
  LOST(LOST_MESSAGE),
  CURRENT_LOCATION(CURRENT_LOCATION_MESSAGE),

  // Emergency Messages
  INJURED_CALL_FOR_HELP(INJURED_CALL_FOR_HELP_MESSAGE),
  NEED_MEDICAL_ATTENTION(NEED_MEDICAL_ATTENTION_MESSAGE);

  private final String messageCode;

  QuickMessageType(String messageCode) {
    this.messageCode = messageCode;
  }
}
