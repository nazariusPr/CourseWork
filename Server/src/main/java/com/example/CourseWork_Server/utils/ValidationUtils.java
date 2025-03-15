package com.example.CourseWork_Server.utils;

import java.util.Set;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

public class ValidationUtils {
  private static final Set<String> ALLOWED_EXTENSIONS =
      Set.of("jpg", "jpeg", "png", "gif", "bmp", "tiff");

  private ValidationUtils() {}

  public static void validateObject(Object object, String objectName) {
    if (object == null) {
      throw new IllegalArgumentException(String.format("Field '%s' cannot be null!", objectName));
    }
  }

  public static void validateImageType(MultipartFile file) {
    validateObject(file, "MultipartFile");
    String extension = FilenameUtils.getExtension(file.getOriginalFilename());

    if (!ALLOWED_EXTENSIONS.contains(extension)) {
      throw new IllegalArgumentException(String.format("Unsupported file type: %s", extension));
    }
  }
}
