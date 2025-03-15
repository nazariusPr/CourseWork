package com.example.CourseWork_Server.constant;

/**
 * Contains constants for message keys used to retrieve localized messages from the message source.
 *
 * <p>Only the fields in this class should be used as keys to ensure consistency across the
 * application.
 *
 * <p>Example usage:
 *
 * <pre>
 * String message = messageSource.getMessage(TextResourceCode.USER_REGISTERED, null, locale);
 * </pre>
 */
public class TextResourceCode {
  private TextResourceCode() {}

  private static final String MESSAGE = "message.";
  private static final String QUICK_MESSAGE = "quick_message.";
  private static final String EXCEPTION = "exception.";
  public static final String USER_REGISTERED = MESSAGE + "user_registered";
  public static final String MAIL_RESENT = MESSAGE + "mail_resent";
  public static final String PASSWORD_CODE_LINK_SENT = MESSAGE + "password_code_link_sent";
  public static final String PASSWORD_RESET_SUCCESS = MESSAGE + "password_reset_success";
  public static final String PASSWORD_CHANGE_SUCCESS = MESSAGE + "password_change_success";
  public static final String USER_LOCATION_MESSAGE = QUICK_MESSAGE + "user_location";
  public static final String NEED_ASSISTANCE_MESSAGE = QUICK_MESSAGE + "need_assistance";
  public static final String FEELING_UNSAFE_MESSAGE = QUICK_MESSAGE + "feeling_unsafe";
  public static final String LOST_MESSAGE = QUICK_MESSAGE + "lost";
  public static final String CURRENT_LOCATION_MESSAGE = QUICK_MESSAGE + "current_location";
  public static final String INJURED_CALL_FOR_HELP_MESSAGE =
      QUICK_MESSAGE + "injured_call_for_help";
  public static final String NEED_MEDICAL_ATTENTION_MESSAGE =
      QUICK_MESSAGE + "need_medical_attention";
  public static final String FIELD_CANNOT_BE_NULL = EXCEPTION + "field_cannot_be_null";
  public static final String OBJECT_CANNOT_BE_NULL = EXCEPTION + "object_cannot_be_null";
  public static final String UNKNOWN_TOKEN_TYPE = EXCEPTION + "unknown_token_type";
  public static final String UNKNOWN_USER_TYPE = EXCEPTION + "unknown_user_type";
  public static final String ENTITY_NOT_FOUND = EXCEPTION + "entity_not_found";
  public static final String PASSWORD_NOT_FOUND = EXCEPTION + "password_not_found";
  public static final String FAILED_TO_RESET_PASSWORD = EXCEPTION + "failed_to_reset_password";
  public static final String HAS_TO_SPECIFY_USER_TYPE = EXCEPTION + "has_to_specify_user_type";
  public static final String NEW_PASSWORD_CONFIRMATION_MISMATCH =
      EXCEPTION + "new_password_confirmation_mismatch";
  public static final String NEW_PASSWORD_SAME_AS_OLD = EXCEPTION + "new_password_same_as_old";
  public static final String INCORRECT_OLD_PASSWORD = EXCEPTION + "incorrect_old_password";
  public static final String FAILED_TO_SEND_EMAIL = EXCEPTION + "failed_to_send_email";
  public static final String ENTITY_ALREADY_EXISTS = EXCEPTION + "entity_already_exists";
  public static final String INVALID_TOKEN = EXCEPTION + "invalid_token";
  public static final String INVALID_CODE = EXCEPTION + "invalid_code";
  public static final String INVALID_EMAIL_TYPE = EXCEPTION + "invalid_email_type";
  public static final String INVALID_PASSWORD = EXCEPTION + "invalid_password";
  public static final String BAD_CREDENTIALS = EXCEPTION + "bad_credentials";
  public static final String INVALID_AGREEMENT_STATUS = EXCEPTION + "invalid_agreement_status";
  public static final String OPERATION_NOT_ALLOWED = EXCEPTION + "operation_not_allowed";
}
