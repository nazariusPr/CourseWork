package com.example.CourseWork_Server.utils;

import static com.example.CourseWork_Server.constant.TextResourceCode.UNKNOWN_TOKEN_TYPE;
import static com.example.CourseWork_Server.utils.ValidationUtils.validateObject;

import com.example.CourseWork_Server.enums.CodeType;
import java.util.Random;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CodeUtils {
  @Value("${code.durations.email-verification-code}")
  private Long emailVerificationCodeDuration;

  @Value("${code.durations.forget-password-code}")
  private Long forgetPasswordCodeDuration;

  private final Random rand = new Random();

  /**
   * Generates a random code of the specified length. The code can be either numeric (digits only)
   * or alphanumeric (digits and letters).
   *
   * @param length the length of the generated code
   * @param onlyDigits if true, the code will only contain digits (0-9); if false, the code may
   *     contain both digits (0-9) and letters (A-Z, a-z)
   * @return a randomly generated code as a String
   */
  public String generateRandomCode(int length, boolean onlyDigits) {
    StringBuilder code = new StringBuilder();

    for (int i = 0; i < length; ++i) {
      if (onlyDigits) {
        int randomDigit = rand.nextInt(10);
        code.append(randomDigit);
      } else {
        code.append(generateRandomAlphaNumericChar());
      }
    }

    return code.toString();
  }

  /**
   * Generates a random numeric code of the specified length. This is a shortcut for generating a
   * code that contains only digits (0-9).
   *
   * @param length the length of the generated numeric code
   * @return a randomly generated numeric code as a String
   */
  public String generateRandomNumericCode(int length) {
    return generateRandomCode(length, true);
  }

  /**
   * Returns the duration of the code based on the type. The duration values are injected from the
   * application.yml configuration.
   *
   * @param type The type of code (e.g., VERIFICATION_CODE, FORGOT_PASSWORD_CODE)
   * @return the duration (in seconds) associated with the code type
   * @throws IllegalArgumentException if the code type is invalid
   */
  public Long getCodeDuration(CodeType type) {
    validateObject(type, "TYPE");

    return switch (type) {
      case VERIFICATION_CODE -> emailVerificationCodeDuration;
      case FORGOT_PASSWORD_CODE -> forgetPasswordCodeDuration;
      default -> throw new IllegalArgumentException(UNKNOWN_TOKEN_TYPE);
    };
  }

  private char generateRandomAlphaNumericChar() {
    if (rand.nextBoolean()) {
      return (char) (rand.nextInt(10) + '0');
    } else {
      boolean isUpperCase = rand.nextBoolean();
      char randomLetter;
      if (isUpperCase) {
        randomLetter = (char) (rand.nextInt(26) + 'A');
      } else {
        randomLetter = (char) (rand.nextInt(26) + 'a');
      }
      return randomLetter;
    }
  }
}
