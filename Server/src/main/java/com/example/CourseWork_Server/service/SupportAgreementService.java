package com.example.CourseWork_Server.service;

import com.example.CourseWork_Server.dto.supportagreement.SupportAgreementDetailDto;
import com.example.CourseWork_Server.dto.supportagreement.SupportAgreementDto;
import java.util.UUID;

public interface SupportAgreementService {
  /**
   * Creates a new support agreement between a support provider and a visually impaired user.
   *
   * @param supportProviderEmail the email of the support provider
   * @param visuallyImpairedEmail the email of the visually impaired user
   * @return the newly created support agreement as a DTO
   */
  SupportAgreementDto create(String supportProviderEmail, String visuallyImpairedEmail);

  /**
   * Retrieves the details of a specific support agreement by its unique identifier.
   *
   * @param id the unique identifier of the support agreement
   * @return the detailed information of the specified support agreement
   */
  SupportAgreementDetailDto read(UUID id);

  /**
   * Updates an existing support agreement based on the provided DTO.
   *
   * @param id the unique identifier (UUID) of the support agreement to be updated.
   * @param agreementDto the DTO containing updated details of the support agreement
   * @return the updated support agreement as a {@link SupportAgreementDto}
   */
  SupportAgreementDto update(UUID id, SupportAgreementDto agreementDto);

  /**
   * Deletes a support agreement from the system.
   *
   * @param id the unique identifier of the support agreement to be deleted
   */
  void delete(UUID id);
}
