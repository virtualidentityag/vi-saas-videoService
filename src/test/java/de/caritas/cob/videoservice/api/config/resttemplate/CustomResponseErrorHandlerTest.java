package de.caritas.cob.videoservice.api.config.resttemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import de.caritas.cob.videoservice.api.exception.httpresponse.InternalServerErrorException;
import de.caritas.cob.videoservice.config.resttemplate.CustomResponseErrorHandler;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class CustomResponseErrorHandlerTest {

  @Mock private ClientHttpResponse httpResponse;

  private final CustomResponseErrorHandler errorHandler = new CustomResponseErrorHandler();

  @Test
  void
      handleError_Should_throwInternalServerErrorException_When_responseStatusIsNotLoopThrough() {
    assertThrows(
        InternalServerErrorException.class,
        () -> {
          URL url = new URL("http://test.de");

          handleErrorGet(url);
        });
  }

  @Test
  void
      handleError_Should_throwExpectedResponseStatusException_When_responseStatusIsForbidden()
          throws Exception {
    URL url = new URL("http://test.de");
    when(httpResponse.getStatusCode()).thenReturn(HttpStatus.FORBIDDEN);

    try {
      handleErrorGet(url);
      fail("Exception was not thrown");
    } catch (ResponseStatusException e) {
      assertThat(e.getStatusCode(), is(HttpStatus.FORBIDDEN));
      assertThat(e.getReason(), is("GET http://test.de"));
    }
  }

  @Test
  void
      handleError_Should_throwExpectedResponseStatusException_When_responseStatusIsNotFound()
          throws Exception {
    URL url = new URL("http://test.de");
    when(httpResponse.getStatusCode()).thenReturn(HttpStatus.NOT_FOUND);

    try {
      handleErrorGet(url);
      fail("Exception was not thrown");
    } catch (ResponseStatusException e) {
      assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
      assertThat(e.getReason(), is("GET http://test.de"));
    }
  }

  private void handleErrorGet(URL url) throws IOException, URISyntaxException {
    errorHandler.handleError(url.toURI(), HttpMethod.GET, httpResponse);
  }

  private void handleErrorPost(URL url) throws IOException, URISyntaxException {
    errorHandler.handleError(url.toURI(), HttpMethod.POST, httpResponse);
  }

  @Test
  void
      handleError_Should_throwExpectedResponseStatusException_When_responseStatusIsBadRequest()
          throws Exception {
    URL url = new URL("http://test.de");
    when(httpResponse.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);

    try {
      handleErrorPost(url);
      fail("Exception was not thrown");
    } catch (ResponseStatusException e) {
      assertThat(e.getStatusCode(), is(HttpStatus.BAD_REQUEST));
      assertThat(e.getReason(), is("POST http://test.de"));
    }
  }
}
