package com.example.CourseWork_Server.security.scheduler;

import static com.example.CourseWork_Server.constant.AppConstants.ZONE_ID;

import com.example.CourseWork_Server.enums.AccountStatus;
import com.example.CourseWork_Server.enums.CodeType;
import com.example.CourseWork_Server.model.User;
import com.example.CourseWork_Server.service.EmailService;
import com.example.CourseWork_Server.service.UserService;
import com.example.CourseWork_Server.utils.CodeUtils;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
@AllArgsConstructor
public class Scheduler {
  private final UserService userService;
  private final EmailService emailService;
  private final CodeUtils codeUtils;

  /** Scheduled method to process unverified users every day at noon. */
  @Scheduled(cron = "0 0 12 * * ?")
  public void processUnverifiedUsers() {
    log.info("**/ Security Scheduler running");

    List<User> users = userService.findByStatus(AccountStatus.NOT_VERIFIED);
    users.parallelStream().forEach(this::processUser);
  }

  private void processUser(User user) {
    try {
      String email = user.getEmail();

      if (isVerificationTimeExpired(user)) {
        userService.delete(email);
      } else {
        emailService.sendReminderVerificationEmail(email);
      }
    } catch (Exception e) {
      log.error("Error processing user " + user.getEmail());
    }
  }

  private boolean isVerificationTimeExpired(User user) {
    LocalDateTime userCreationTime = user.getCreatedAt();

    long userCreationTimeMillis =
        userCreationTime.atZone(ZoneId.of(ZONE_ID)).toInstant().toEpochMilli();
    long verificationTIme = System.currentTimeMillis() - userCreationTimeMillis;

    return verificationTIme > codeUtils.getCodeDuration(CodeType.VERIFICATION_CODE);
  }
}
