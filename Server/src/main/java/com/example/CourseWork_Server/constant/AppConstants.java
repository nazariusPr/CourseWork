package com.example.CourseWork_Server.constant;

public class AppConstants {
  private AppConstants() {}

  public static final String GET_LOCATION_URL =
      "https://nominatim.openstreetmap.org/reverse?lat=%f&lon=%f&format=json&accept-language=%s";
  public static final String ZONE_ID = "Europe/Kyiv";
  public static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";
  public static final String VERIFICATION_CODE_PREFIX = "code:verification:";
  public static final String FORGOT_PASSWORD_CODE_PREFIX = "code:forgot-password:";
  public static final String CACHE_PREFIX = "cache:api-response:";
  public static final String AUTH_LINK = "/api/v1/auth";
  public static final String SUPPORT_AGREEMENT_LINK = "/api/v1/support-agreement";
  public static final String QUICK_MESSAGE_LINK = "/api/v1/quick-message";
  public static final String LOCATION_LINK = "/api/v1/location";
  public static final String ANALYZE_LINK = "/api/v1/analyze";
}
