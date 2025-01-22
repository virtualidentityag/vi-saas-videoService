package de.caritas.cob.videoservice.api.service.httpheader;

import static de.caritas.cob.videoservice.api.testhelper.FieldConstants.FIELD_NAME_CSRF_TOKEN_COOKIE_PROPERTY;
import static de.caritas.cob.videoservice.api.testhelper.FieldConstants.FIELD_NAME_CSRF_TOKEN_HEADER_PROPERTY;
import static de.caritas.cob.videoservice.api.testhelper.FieldConstants.FIELD_VALUE_CSRF_TOKEN_COOKIE_PROPERTY;
import static de.caritas.cob.videoservice.api.testhelper.FieldConstants.FIELD_VALUE_CSRF_TOKEN_HEADER_PROPERTY;
import static de.caritas.cob.videoservice.api.testhelper.TestConstants.BEARER_TOKEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import de.caritas.cob.videoservice.api.authorization.AuthenticatedUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@ExtendWith(MockitoExtension.class)
class SecurityHeaderSupplierTest {

  @InjectMocks private SecurityHeaderSupplier securityHeaderSupplier;
  @Mock private AuthenticatedUser videoUser;

  @BeforeEach
  void setup() {
    setField(
        securityHeaderSupplier,
        FIELD_NAME_CSRF_TOKEN_HEADER_PROPERTY,
        FIELD_VALUE_CSRF_TOKEN_HEADER_PROPERTY);
    setField(
        securityHeaderSupplier,
        FIELD_NAME_CSRF_TOKEN_COOKIE_PROPERTY,
        FIELD_VALUE_CSRF_TOKEN_COOKIE_PROPERTY);
  }

  @Test
  void getKeycloakAndCsrfHttpHeaders_Should_Return_HeaderWithCorrectContentType() {
    HttpHeaders result = securityHeaderSupplier.getKeycloakAndCsrfHttpHeaders();

    assertEquals(MediaType.APPLICATION_JSON, result.getContentType());
  }

  @Test
  void getKeycloakAndCsrfHttpHeaders_Should_Return_CorrectCookiePropertyName() {
    HttpHeaders result = securityHeaderSupplier.getKeycloakAndCsrfHttpHeaders();

    assertTrue(
        result
            .get("Cookie")
            .toString()
            .startsWith("[" + FIELD_VALUE_CSRF_TOKEN_COOKIE_PROPERTY + "="));
  }

  @Test
  void getKeycloakAndCsrfHttpHeaders_Should_Return_CorrectHeaderAndCookieValues() {
    HttpHeaders result = securityHeaderSupplier.getKeycloakAndCsrfHttpHeaders();
    String cookieValue =
        "["
            + result
                .get("Cookie")
                .toString()
                .substring(result.get("Cookie").toString().lastIndexOf("=") + 1);

    assertEquals(cookieValue, result.get(FIELD_VALUE_CSRF_TOKEN_HEADER_PROPERTY).toString());
  }

  @Test
  void getRocketChatAndCsrfHttpHeaders_Should_ReturnHeaderWithKeycloakAuthToken() {
    when(videoUser.getAccessToken()).thenReturn(BEARER_TOKEN);

    HttpHeaders result = securityHeaderSupplier.getKeycloakAndCsrfHttpHeaders();

    assertEquals("[Bearer " + BEARER_TOKEN + "]", result.get("Authorization").toString());
  }
}
