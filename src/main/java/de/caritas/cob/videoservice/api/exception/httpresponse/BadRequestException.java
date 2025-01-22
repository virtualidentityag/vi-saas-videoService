package de.caritas.cob.videoservice.api.exception.httpresponse;

import java.io.Serial;
import java.util.function.Consumer;

public class BadRequestException extends CustomHttpStatusException {

  @Serial private static final long serialVersionUID = -3553609955386498237L;

  /**
   * BadRequest exception.
   *
   * @param message an additional message
   * @param loggingMethod the method being used to log this exception
   */
  public BadRequestException(String message, Consumer<Exception> loggingMethod) {
    super(message, loggingMethod);
  }
}
