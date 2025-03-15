package com.example.CourseWork_Server.service;

import com.example.CourseWork_Server.dto.general.MessageDto;
import org.springframework.web.multipart.MultipartFile;

public interface AnalyzeService {
  /**
   * Analyzes the provided image file and returns the analysis result as a string.
   *
   * <p><b>Note:</b> The provided file must be of an image type (e.g., JPEG, PNG, BMP, etc.).
   * Uploading non-image files may result in unexpected behavior or errors.
   *
   * @param multipartFile the image file to be analyzed.
   * @return MessageDto containing the result of the analysis.
   */
  MessageDto analyze(MultipartFile multipartFile);
}
