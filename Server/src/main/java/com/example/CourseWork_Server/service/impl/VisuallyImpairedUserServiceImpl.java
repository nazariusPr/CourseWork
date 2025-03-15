package com.example.CourseWork_Server.service.impl;

import com.example.CourseWork_Server.model.User;
import com.example.CourseWork_Server.model.VisuallyImpairedUser;
import com.example.CourseWork_Server.repository.VisuallyImpairedUserRepository;
import com.example.CourseWork_Server.service.VisuallyImpairedUserService;
import com.example.CourseWork_Server.utils.ExceptionUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VisuallyImpairedUserServiceImpl implements VisuallyImpairedUserService {
  private final VisuallyImpairedUserRepository repository;
  private final ExceptionUtils exceptionUtils;

  /** {@inheritDoc} */
  @Override
  public VisuallyImpairedUser create(User user) {
    VisuallyImpairedUser impairedUser = new VisuallyImpairedUser();
    impairedUser.setUser(user);

    return repository.save(impairedUser);
  }

  /** {@inheritDoc} */
  @Override
  public VisuallyImpairedUser findByEmail(String email) {
    return repository
        .findByEmail(email)
        .orElseThrow(() -> exceptionUtils.entityNotFoundException("VisuallyImpairedUser", email));
  }
}
