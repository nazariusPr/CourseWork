package com.example.CourseWork_Server.utils;

import com.example.CourseWork_Server.dto.supportagreement.SupportAgreementDetailDto;
import com.example.CourseWork_Server.model.User;
import com.example.CourseWork_Server.service.SupportAgreementService;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component("securityUtils")
@AllArgsConstructor
public class SecurityUtils {
  private final SupportAgreementService supportAgreementService;

  public static List<GrantedAuthority> getUserAuthorities(User user) {
    return List.of(new SimpleGrantedAuthority("ROLE_" + user.getUserType().name()));
  }

  public boolean hasAccessToSupportAgreement(UUID agreementId, Authentication authentication) {
    String email = (String) authentication.getPrincipal();
    SupportAgreementDetailDto agreementDetailDto = supportAgreementService.read(agreementId);

    return agreementDetailDto.getSupportProviderEmail().equals(email)
        || agreementDetailDto.getVisuallyImpairedEmail().equals(email);
  }
}
