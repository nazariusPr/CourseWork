package com.example.CourseWork_Server.security.service.impl;

import static com.example.CourseWork_Server.constant.AppConstants.FORGOT_PASSWORD_CODE_PREFIX;
import static com.example.CourseWork_Server.constant.AppConstants.VERIFICATION_CODE_PREFIX;
import static com.example.CourseWork_Server.constant.TextResourceCode.UNKNOWN_TOKEN_TYPE;

import com.example.CourseWork_Server.enums.CodeType;
import com.example.CourseWork_Server.security.service.CodeStoreService;
import com.example.CourseWork_Server.utils.ExceptionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CodeStoreServiceImpl implements CodeStoreService {
  private final RedisTemplate<String, Object> redisTemplate;
  private final ObjectMapper objectMapper;
  private final ExceptionUtils exceptionUtils;

  /** {@inheritDoc} */
  @Override
  public String setCode(String email, String code, CodeType type, Long duration) {
    String key = getKey(code, type);

    redisTemplate.opsForValue().set(key, email, duration, TimeUnit.MILLISECONDS);
    return key;
  }

  /** {@inheritDoc} */
  @Override
  public String getEmailByKey(String key) {
    Object object = redisTemplate.opsForValue().get(key);
    return objectMapper.convertValue(object, String.class);
  }

  /** {@inheritDoc} */
  @Override
  public String getEmailByCode(String code, CodeType type) {
    String key = getKey(code, type);
    return getEmailByKey(key);
  }

  /** {@inheritDoc} */
  @Override
  public void deleteCode(String code, CodeType type) {
    String key = getKey(code, type);
    redisTemplate.delete(key);
  }

  private String getKey(String code, CodeType type) {
    String prefix =
        switch (type) {
          case VERIFICATION_CODE -> VERIFICATION_CODE_PREFIX;
          case FORGOT_PASSWORD_CODE -> FORGOT_PASSWORD_CODE_PREFIX;
          default -> throw exceptionUtils.illegalArgumentException(UNKNOWN_TOKEN_TYPE, type);
        };

    return prefix + code;
  }
}
