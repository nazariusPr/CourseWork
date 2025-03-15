package com.example.CourseWork_Server.service;

import com.example.CourseWork_Server.dto.quickmessage.QuickMessageDetailDto;
import com.example.CourseWork_Server.dto.quickmessage.QuickMessageDto;
import com.example.CourseWork_Server.model.QuickMessage;
import com.example.CourseWork_Server.model.QuickMessageBox;
import java.util.List;
import java.util.UUID;

public interface QuickMessageService {
  /**
   * Sends a quick message from the specified sender to a recipient.
   *
   * @param senderEmail the email of the sender.
   * @param quickMessageDto the {@link QuickMessageDto} containing the message content and type.
   * @return a {@link QuickMessageDetailDto} representing the sent message details.
   */
  QuickMessageDetailDto sendQuickMessage(String senderEmail, QuickMessageDto quickMessageDto);

  /**
   * Retrieves the details of a specific quick message by its unique identifier.
   *
   * @param id the unique identifier (UUID) of the message.
   * @return a {@link QuickMessageDetailDto} containing the details of the specified message.
   */
  QuickMessageDetailDto read(UUID id);

  /**
   * Retrieves a list of all quick messages stored in the system.
   *
   * @return a list of {@link QuickMessageDetailDto} representing all quick messages.
   */
  List<QuickMessageDetailDto> read(String email);

  /**
   * Creates a new quick message inside a specific message box.
   *
   * @param messageBox the {@link QuickMessageBox} where the message will be stored.
   * @param messageDto the {@link QuickMessageDetailDto} containing the message content and
   *     metadata.
   * @return a {@link QuickMessage} representing the newly created quick message.
   */
  QuickMessage create(QuickMessageBox messageBox, QuickMessageDetailDto messageDto);
}
