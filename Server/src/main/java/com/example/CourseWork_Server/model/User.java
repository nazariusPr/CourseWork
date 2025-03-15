package com.example.CourseWork_Server.model;

import static com.example.CourseWork_Server.constant.AppConstants.ZONE_ID;

import com.example.CourseWork_Server.enums.AccountStatus;
import com.example.CourseWork_Server.enums.AuthMethod;
import com.example.CourseWork_Server.enums.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Email
  @NotBlank
  @Column(nullable = false, unique = true)
  private String email;

  @Column(name = "user_type")
  @Enumerated(EnumType.STRING)
  private UserType userType;

  @Column(name = "account_status")
  @Enumerated(EnumType.STRING)
  private AccountStatus accountStatus;

  @Column(name = "auth_method")
  @Enumerated(EnumType.STRING)
  private AuthMethod authMethod;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
  private QuickMessageBox quickMessageBox;

  @PrePersist
  private void prePersist() {
    if (createdAt == null) {
      createdAt = ZonedDateTime.now(ZoneId.of(ZONE_ID)).toLocalDateTime();
    }
  }
}
