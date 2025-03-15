package com.example.CourseWork_Server.model;

import static com.example.CourseWork_Server.constant.AppConstants.ZONE_ID;

import com.example.CourseWork_Server.enums.SupportAgreementStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "support_agreements")
@Entity
public class SupportAgreement {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "support_provider_id", nullable = false)
  private SupportProviderUser supportProviderUser;

  @ManyToOne
  @JoinColumn(name = "visually_impaired_user_id", nullable = false)
  private VisuallyImpairedUser visuallyImpairedUser;

  @Column(name = "agreement_date", nullable = false)
  private LocalDateTime agreementDate;

  @Column(name = "agreement_status", nullable = false)
  @Enumerated(EnumType.STRING)
  private SupportAgreementStatus supportAgreementStatus;

  @PrePersist
  private void prePersist() {
    if (agreementDate == null) {
      agreementDate = ZonedDateTime.now(ZoneId.of(ZONE_ID)).toLocalDateTime();
    }
  }
}
