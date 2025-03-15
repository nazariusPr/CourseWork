package com.example.CourseWork_Server.security.service.impl;

import static com.example.CourseWork_Server.constant.TextResourceCode.INCORRECT_OLD_PASSWORD;
import static com.example.CourseWork_Server.constant.TextResourceCode.NEW_PASSWORD_CONFIRMATION_MISMATCH;
import static com.example.CourseWork_Server.constant.TextResourceCode.NEW_PASSWORD_SAME_AS_OLD;
import static com.example.CourseWork_Server.constant.TextResourceCode.PASSWORD_NOT_FOUND;

import com.example.CourseWork_Server.model.Credential;
import com.example.CourseWork_Server.model.User;
import com.example.CourseWork_Server.security.dto.password.ChangePasswordDto;
import com.example.CourseWork_Server.security.dto.password.PasswordDto;
import com.example.CourseWork_Server.security.repository.CredentialRepository;
import com.example.CourseWork_Server.security.service.CredentialService;
import com.example.CourseWork_Server.service.UserService;
import com.example.CourseWork_Server.utils.ExceptionUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CredentialServiceImpl implements CredentialService {
  private final UserService userService;
  private final CredentialRepository repository;
  private final PasswordEncoder encoder;
  private final ExceptionUtils exceptionUtils;

  /** {@inheritDoc} */
  @Override
  public Credential findByUser(User user) {
    return repository
        .findByUserId(user.getId())
        .orElseThrow(() -> exceptionUtils.badCredentialsException(PASSWORD_NOT_FOUND));
  }

  /** {@inheritDoc} */
  @Override
  public Credential findByUserEmail(String userEmail) {
    User user = userService.findByEmail(userEmail);
    return findByUser(user);
  }

  /** {@inheritDoc} */
  @Override
  public Credential create(String password, User user) {
    Credential credential = new Credential();
    credential.setPassword(encoder.encode(password));
    credential.setUser(user);

    return repository.save(credential);
  }

  /** {@inheritDoc} */
  @Override
  public Credential changePassword(PasswordDto passwordDto, User user) {
    validatePasswordConfirmation(passwordDto);
    Credential credential = findByUser(user);

    if (passwordDto instanceof ChangePasswordDto changePasswordDto) {
      validateOldPassword(changePasswordDto.getOldPassword(), credential);
    }

    if (encoder.matches(passwordDto.getPassword(), credential.getPassword())) {
      throw exceptionUtils.badCredentialsException(NEW_PASSWORD_SAME_AS_OLD);
    }

    credential.setPassword(encoder.encode(passwordDto.getPassword()));

    return repository.save(credential);
  }

  /** {@inheritDoc} */
  @Override
  public Credential changePassword(PasswordDto passwordDto, String userEmail) {
    User user = userService.findByEmail(userEmail);
    return changePassword(passwordDto, user);
  }

  private void validateOldPassword(String oldPassword, Credential credential) {
    if (!encoder.matches(oldPassword, credential.getPassword())) {
      throw exceptionUtils.badCredentialsException(INCORRECT_OLD_PASSWORD);
    }
  }

  private void validatePasswordConfirmation(PasswordDto passwordDto) {
    if (!passwordDto.getPassword().equals(passwordDto.getConfirmPassword())) {
      throw exceptionUtils.badCredentialsException(NEW_PASSWORD_CONFIRMATION_MISMATCH);
    }
  }
}
