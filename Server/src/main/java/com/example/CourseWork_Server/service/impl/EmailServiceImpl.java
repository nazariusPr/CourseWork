package com.example.CourseWork_Server.service.impl;

import static com.example.CourseWork_Server.constant.TextResourceCode.FAILED_TO_SEND_EMAIL;

import com.example.CourseWork_Server.enums.EmailType;
import com.example.CourseWork_Server.exception.exceptions.EmailSendingException;
import com.example.CourseWork_Server.service.EmailService;
import com.example.CourseWork_Server.utils.ExceptionUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Locale;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {
  private final JavaMailSender mailSender;
  private final TemplateEngine templateEngine;
  private final MessageSource messageSource;
  private final ExceptionUtils exceptionUtils;

  /** {@inheritDoc} */
  @Override
  public void sendVerificationEmail(String to, String verificationCode) {
    Map<String, Object> variables = Map.of("verificationCode", verificationCode);
    sendEmail(to, EmailType.VERIFICATION_EMAIL, variables);
  }

  /** {@inheritDoc} */
  @Override
  public void sendForgotPasswordEmail(String to, String forgotPasswordCode) {
    Map<String, Object> variables = Map.of("forgotPasswordCode", forgotPasswordCode);
    sendEmail(to, EmailType.FORGOT_PASSWORD_EMAIL, variables);
  }

  /** {@inheritDoc} */
  @Override
  public void sendReminderVerificationEmail(String to) {
    sendEmail(to, EmailType.REMINDER_EMAIL, null);
  }

  private void sendEmail(String to, EmailType emailType, Map<String, Object> variables) {
    Locale locale = LocaleContextHolder.getLocale();

    String templateName = emailType.getTemplateName();
    String subject = messageSource.getMessage(emailType.getSubjectKey(), null, locale);
    String content = createEmailTemplate(variables, templateName, locale);

    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

    try {
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(content, true);
    } catch (MessagingException e) {
      throw exceptionUtils.createLocalizedException(
          FAILED_TO_SEND_EMAIL, EmailSendingException.class);
    }

    mailSender.send(mimeMessage);
  }

  private String createEmailTemplate(Map<String, Object> vars, String templateName, Locale locale) {
    Context context = new Context(locale);
    context.setVariables(vars);
    return templateEngine.process(templateName, context);
  }
}
