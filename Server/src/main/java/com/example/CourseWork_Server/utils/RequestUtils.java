package com.example.CourseWork_Server.utils;

import com.example.CourseWork_Server.dto.general.ExceptionDto;
import com.example.CourseWork_Server.exception.exceptions.BadRequestException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class RequestUtils {

  private final WebClient webClient;

  /**
   * Sends a POST request to the given URI with the specified request body and returns a response of
   * the given type.
   *
   * @param uri The URI to which the POST request will be sent.
   * @param requestBody The request body to be sent in the POST request.
   * @param responseType The class type of the expected response body.
   * @param <T> The type of the expected response body.
   * @return The response of the given type from the server.
   */
  public <T> T postRequest(String uri, Object requestBody, Class<T> responseType) {
    return webClient
        .post()
        .uri(uri)
        .headers(getDefaultHeaders())
        .bodyValue(requestBody)
        .retrieve()
        .onStatus(this::isErrorStatus, this::handleError)
        .bodyToMono(responseType)
        .block();
  }

  /**
   * Sends a POST request with a multipart file to the given URI and returns a response of the given
   * type.
   *
   * @param uri The URI to which the POST request will be sent.
   * @param multipartFile The file to be sent in the request.
   * @param multipartName The name of the multipart field.
   * @param responseType The class type of the expected response body.
   * @param <T> The type of the expected response body.
   * @return The response of the given type from the server.
   */
  public <T> T postRequest(
      String uri, MultipartFile multipartFile, String multipartName, Class<T> responseType) {
    MultipartBodyBuilder builder = new MultipartBodyBuilder();
    builder.part(multipartName, multipartFile.getResource());

    return webClient
        .post()
        .uri(uri)
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .headers(getDefaultHeaders())
        .body(BodyInserters.fromMultipartData(builder.build()))
        .retrieve()
        .onStatus(this::isErrorStatus, this::handleError)
        .bodyToMono(responseType)
        .block();
  }

  /**
   * Sends a GET request to the given URI with optional query parameters and URI variables and
   * returns a response of the specified type.
   *
   * @param uri The URI to which the GET request will be sent.
   * @param responseType The class type of the expected response body.
   * @param uriVariables Optional URI variables to be used in the URI template.
   * @param <T> The type of the expected response body.
   * @return The response of the given type from the server.
   */
  public <T> T getRequest(String uri, Class<T> responseType, Object... uriVariables) {
    return webClient
        .get()
        .uri(uri, uriVariables)
        .retrieve()
        .onStatus(this::isErrorStatus, this::handleError)
        .bodyToMono(responseType)
        .block();
  }

  /**
   * Handles the error response by mapping it to a BadRequestException with the provided error
   * message.
   *
   * @param response The client response containing the error details.
   * @return A Mono containing the BadRequestException with the error message.
   */
  private Mono<? extends Throwable> handleError(ClientResponse response) {
    return response
        .bodyToMono(ExceptionDto.class)
        .map(error -> new BadRequestException(error.getMessage()));
  }

  /**
   * Checks whether the given HTTP status code represents an error status (4xx or 5xx).
   *
   * @param status The HTTP status code to check.
   * @return True if the status is a client-side (4xx) or server-side (5xx) error, false otherwise.
   */
  private boolean isErrorStatus(HttpStatusCode status) {
    return status.is4xxClientError() || status.is5xxServerError();
  }

  /**
   * Returns default headers.
   *
   * @return A map containing default headers.
   */
  private Consumer<HttpHeaders> getDefaultHeaders() {
    Map<String, String> headers = new HashMap<>();
    headers.put(HttpHeaders.ACCEPT_LANGUAGE, LocaleContextHolder.getLocale().toLanguageTag());

    return h -> h.setAll(headers);
  }
}
