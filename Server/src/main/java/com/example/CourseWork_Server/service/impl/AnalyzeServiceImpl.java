package com.example.CourseWork_Server.service.impl;

import static com.example.CourseWork_Server.utils.ValidationUtils.validateImageType;

import com.example.CourseWork_Server.dto.general.MessageDto;
import com.example.CourseWork_Server.service.AnalyzeService;
import com.example.CourseWork_Server.utils.RequestUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AnalyzeServiceImpl implements AnalyzeService {
  private final RequestUtils requestUtils;

  @Value("${analyzer.url}")
  private String analyzerUrl;

  /** {@inheritDoc} */
  @Override
  public MessageDto analyze(MultipartFile multipartFile) {
    validateImageType(multipartFile);

    String url = analyzerUrl + "/api/v1/analyze";
    String multipartName = "image";

    return requestUtils.postRequest(url, multipartFile, multipartName, MessageDto.class);
  }
}
