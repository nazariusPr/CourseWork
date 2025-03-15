package com.example.CourseWork_Server.service.impl;

import static com.example.CourseWork_Server.constant.TextResourceCode.ENTITY_ALREADY_EXISTS;

import com.example.CourseWork_Server.enums.AccountStatus;
import com.example.CourseWork_Server.enums.AuthMethod;
import com.example.CourseWork_Server.mapper.UserMapper;
import com.example.CourseWork_Server.model.User;
import com.example.CourseWork_Server.repository.UserRepository;
import com.example.CourseWork_Server.security.dto.auth.RegisterDto;
import com.example.CourseWork_Server.service.QuickMessageBoxService;
import com.example.CourseWork_Server.service.UserService;
import com.example.CourseWork_Server.utils.ExceptionUtils;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
  private final QuickMessageBoxService quickMessageBoxService;
  private final UserRepository repository;
  private final UserMapper mapper;
  private final ExceptionUtils exceptionUtils;

  /** {@inheritDoc} */
  @Override
  public User findByEmail(String email) {
    return repository
        .findByEmail(email)
        .orElseThrow(() -> exceptionUtils.entityNotFoundException("User", email));
  }

  /** {@inheritDoc} */
  @Override
  public List<User> findByStatus(AccountStatus status) {
    return repository.findByStatus(status);
  }

  /** {@inheritDoc} */
  @Override
  public User save(User user) {
    validateUserNonExistence(user.getEmail());
    return repository.save(user);
  }

  /** {@inheritDoc} */
  @Override
  public User create(RegisterDto registerDto, AuthMethod authMethod, AccountStatus accountStatus) {
    return create(registerDto, authMethod, accountStatus, true);
  }

  /** {@inheritDoc} */
  @Override
  public User create(
      RegisterDto registerDto,
      AuthMethod authMethod,
      AccountStatus accountStatus,
      boolean persist) {
    User user = mapper.dtoToEntity(registerDto);
    user.setAuthMethod(authMethod);
    user.setAccountStatus(accountStatus);

    if (persist) {
      user = save(user);
      quickMessageBoxService.create(user);
    }
    return user;
  }

  /** {@inheritDoc} */
  @Override
  public void delete(String email) {
    User user = findByEmail(email);
    repository.delete(user);
  }

  /** {@inheritDoc} */
  @Override
  public User changeAccountStatus(String email, AccountStatus status) {
    User user = findByEmail(email);
    return changeAccountStatus(user, status);
  }

  /** {@inheritDoc} */
  @Override
  public User changeAccountStatus(User user, AccountStatus status) {
    user.setAccountStatus(status);
    return repository.save(user);
  }

  /** {@inheritDoc} */
  @Override
  public boolean doesUserExist(String email) {
    return repository.existsByEmail(email);
  }

  private void validateUserNonExistence(String email) {
    if (doesUserExist(email)) {
      throw exceptionUtils.badRequestException(ENTITY_ALREADY_EXISTS, "User");
    }
  }
}
