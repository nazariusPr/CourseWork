package com.example.CourseWork_Server.mapper;

import com.example.CourseWork_Server.dto.supportagreement.SupportAgreementDetailDto;
import com.example.CourseWork_Server.dto.supportagreement.SupportAgreementDto;
import com.example.CourseWork_Server.model.SupportAgreement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SupportAgreementMapper {
  @Mapping(source = "supportProviderUser.user.email", target = "supportProviderEmail")
  @Mapping(source = "visuallyImpairedUser.user.email", target = "visuallyImpairedEmail")
  SupportAgreementDetailDto entityToDetailDto(SupportAgreement agreement);

  SupportAgreementDto entityToDto(SupportAgreement agreement);
}
