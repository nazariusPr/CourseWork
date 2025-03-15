package com.example.CourseWork_Server.service.impl;

import com.example.CourseWork_Server.model.SupportProviderUser;
import com.example.CourseWork_Server.model.User;
import com.example.CourseWork_Server.repository.SupportProviderUserRepository;
import com.example.CourseWork_Server.service.SupportProviderUserService;
import com.example.CourseWork_Server.utils.ExceptionUtils;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SupportProviderUserServiceImpl implements SupportProviderUserService {
  private final SupportProviderUserRepository repository;
  private final ExceptionUtils exceptionUtils;

  /** {@inheritDoc} */
  @Override
  public SupportProviderUser create(User user) {
    SupportProviderUser providerUser = new SupportProviderUser();
    providerUser.setUser(user);

    return repository.save(providerUser);
  }

  /** {@inheritDoc} */
  @Override
  public SupportProviderUser findByEmail(String email) {
    return repository
        .findByEmail(email)
        .orElseThrow(() -> exceptionUtils.entityNotFoundException("SupportProviderUser", email));
  }

  /** {@inheritDoc} */
  @Override
  public List<SupportProviderUser> findByVisuallyImpairedUserEmail(String email) {
    return repository.findByVisuallyImpairedUserEmail(email);
  }
}
