package com.example.CourseWork_Server.service.impl;

import static com.example.CourseWork_Server.constant.TextResourceCode.USER_LOCATION_MESSAGE;

import com.example.CourseWork_Server.dto.location.LocationDto;
import com.example.CourseWork_Server.dto.quickmessage.QuickMessageDetailDto;
import com.example.CourseWork_Server.dto.quickmessage.QuickMessageDto;
import com.example.CourseWork_Server.enums.QuickMessageType;
import com.example.CourseWork_Server.mapper.QuickMessageMapper;
import com.example.CourseWork_Server.model.QuickMessage;
import com.example.CourseWork_Server.model.QuickMessageBox;
import com.example.CourseWork_Server.model.SupportProviderUser;
import com.example.CourseWork_Server.repository.QuickMessageRepository;
import com.example.CourseWork_Server.service.LocationService;
import com.example.CourseWork_Server.service.QuickMessageService;
import com.example.CourseWork_Server.service.SupportProviderUserService;
import com.example.CourseWork_Server.utils.ExceptionUtils;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuickMessageServiceImpl implements QuickMessageService {
  private final SupportProviderUserService supportProviderUserService;
  private final LocationService locationService;
  private final QuickMessageRepository repository;
  private final QuickMessageMapper mapper;
  private final MessageSource messageSource;
  private final ExceptionUtils exceptionUtils;

  /** {@inheritDoc} */
  @Override
  public QuickMessageDetailDto sendQuickMessage(
      String senderEmail, QuickMessageDto quickMessageDto) {
    List<SupportProviderUser> supportUsers =
        supportProviderUserService.findByVisuallyImpairedUserEmail(senderEmail);
    QuickMessageDetailDto detailDto = buildDetailDto(senderEmail, quickMessageDto);

    CompletableFuture.runAsync(
        () -> supportUsers.forEach(user -> create(user.getUser().getQuickMessageBox(), detailDto)));

    return detailDto;
  }

  /** {@inheritDoc} */
  @Override
  public QuickMessageDetailDto read(UUID id) {
    return mapper.toDetailDto(findById(id));
  }

  /** {@inheritDoc} */
  @Override
  public List<QuickMessageDetailDto> read(String email) {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public QuickMessage create(QuickMessageBox messageBox, QuickMessageDetailDto detailDto) {
    QuickMessage quickMessage = mapper.toEntity(detailDto);
    quickMessage.setQuickMessageBox(messageBox);
    return repository.save(quickMessage);
  }

  private QuickMessage findById(UUID id) {
    return repository
        .findById(id)
        .orElseThrow(() -> exceptionUtils.entityNotFoundException("QuickMessage", id));
  }

  private QuickMessageDetailDto buildDetailDto(String senderEmail, QuickMessageDto messageDto) {
    Locale locale = LocaleContextHolder.getLocale();
    QuickMessageType type = messageDto.getQuickMessageType();
    LocationDto locationDto =
        locationService.getLocationDto(messageDto.getCoordinatesDto(), locale);

    String messageContent = generateQuickMessage(senderEmail, locationDto, type, locale);

    return QuickMessageDetailDto.builder()
        .content(messageContent)
        .quickMessageType(messageDto.getQuickMessageType())
        .coordinatesDto(messageDto.getCoordinatesDto())
        .build();
  }

  private String generateQuickMessage(
      String userEmail, LocationDto location, QuickMessageType messageType, Locale locale) {
    String messageCode = messageType.getMessageCode();
    String messageContent = messageSource.getMessage(messageCode, null, locale);

    return String.format(
        messageSource.getMessage(USER_LOCATION_MESSAGE, null, locale),
        userEmail,
        messageContent,
        location.getDisplayName(),
        location.getLat(),
        location.getLon());
  }
}
