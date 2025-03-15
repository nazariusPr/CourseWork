package com.example.CourseWork_Server.service.impl;

import com.example.CourseWork_Server.model.QuickMessageBox;
import com.example.CourseWork_Server.model.User;
import com.example.CourseWork_Server.repository.QuickMessageBoxRepository;
import com.example.CourseWork_Server.service.QuickMessageBoxService;
import com.example.CourseWork_Server.utils.ExceptionUtils;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuickMessageBoxServiceImpl implements QuickMessageBoxService {
  private final QuickMessageBoxRepository repository;
  private final ExceptionUtils exceptionUtils;

  /** {@inheritDoc} */
  @Override
  public QuickMessageBox findById(UUID id) {
    return repository
        .findById(id)
        .orElseThrow(() -> exceptionUtils.entityNotFoundException("MessageBox", id));
  }

  /** {@inheritDoc} */
  @Override
  public QuickMessageBox findByUserId(UUID userId) {
    return repository
        .findByUserId(userId)
        .orElseThrow(() -> exceptionUtils.entityNotFoundException("User", userId));
  }

  /** {@inheritDoc} */
  @Override
  public QuickMessageBox create(User user) {
    QuickMessageBox quickMessageBox = new QuickMessageBox();
    quickMessageBox.setUser(user);

    return repository.save(quickMessageBox);
  }
}
