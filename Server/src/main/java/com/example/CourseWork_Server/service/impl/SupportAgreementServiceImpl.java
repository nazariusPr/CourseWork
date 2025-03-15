package com.example.CourseWork_Server.service.impl;

import static com.example.CourseWork_Server.constant.TextResourceCode.INVALID_AGREEMENT_STATUS;

import com.example.CourseWork_Server.dto.supportagreement.SupportAgreementDetailDto;
import com.example.CourseWork_Server.dto.supportagreement.SupportAgreementDto;
import com.example.CourseWork_Server.enums.SupportAgreementStatus;
import com.example.CourseWork_Server.mapper.SupportAgreementMapper;
import com.example.CourseWork_Server.model.SupportAgreement;
import com.example.CourseWork_Server.model.SupportProviderUser;
import com.example.CourseWork_Server.model.VisuallyImpairedUser;
import com.example.CourseWork_Server.repository.SupportAgreementRepository;
import com.example.CourseWork_Server.service.SupportAgreementService;
import com.example.CourseWork_Server.service.SupportProviderUserService;
import com.example.CourseWork_Server.service.VisuallyImpairedUserService;
import com.example.CourseWork_Server.utils.ExceptionUtils;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SupportAgreementServiceImpl implements SupportAgreementService {
  private final SupportProviderUserService supportProviderUserService;
  private final VisuallyImpairedUserService visuallyImpairedUserService;
  private final SupportAgreementRepository repository;
  private final SupportAgreementMapper mapper;
  private final ExceptionUtils exceptionUtils;

  /** {@inheritDoc} */
  @Override
  public SupportAgreementDto create(String supportProviderEmail, String visuallyImpairedEmail) {
    SupportProviderUser supportProviderUser =
        supportProviderUserService.findByEmail(supportProviderEmail);
    VisuallyImpairedUser visuallyImpairedUser =
        visuallyImpairedUserService.findByEmail(visuallyImpairedEmail);

    SupportAgreement agreement =
        SupportAgreement.builder()
            .supportAgreementStatus(SupportAgreementStatus.PENDING)
            .supportProviderUser(supportProviderUser)
            .visuallyImpairedUser(visuallyImpairedUser)
            .build();

    return mapper.entityToDto(repository.save(agreement));
  }

  /** {@inheritDoc} */
  @Override
  public SupportAgreementDetailDto read(UUID id) {
    return mapper.entityToDetailDto(findById(id));
  }

  /** {@inheritDoc} */
  @Override
  public SupportAgreementDto update(UUID id, SupportAgreementDto agreementDto) {
    SupportAgreementStatus status = agreementDto.getSupportAgreementStatus();

    if (status == SupportAgreementStatus.PENDING) {
      throw exceptionUtils.badRequestException(INVALID_AGREEMENT_STATUS);
    }

    SupportAgreement agreement = findById(id);
    agreement.setSupportAgreementStatus(status);

    return mapper.entityToDto(repository.save(agreement));
  }

  /** {@inheritDoc} */
  @Override
  public void delete(UUID id) {
    repository.delete(findById(id));
  }

  private SupportAgreement findById(UUID id) {
    return repository
        .findById(id)
        .orElseThrow(() -> exceptionUtils.entityNotFoundException("SupportAgreement", id));
  }
}
