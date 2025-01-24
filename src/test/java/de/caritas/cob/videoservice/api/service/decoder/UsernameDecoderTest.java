package de.caritas.cob.videoservice.api.service.decoder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class UsernameDecoderTest {

  private static final String USERNAME_DECODED = "Username!#123";
  private static final String USERNAME_ENCODED = "enc.KVZWK4TOMFWWKIJDGEZDG...";

  @Test
  void decodeUsername_Should_ReturnDecodedUsername_WhenEncodedUsernameIsGiven() {
    String decodedUsername = new UsernameDecoder().decodeUsername(USERNAME_ENCODED);

    assertThat(decodedUsername, is(USERNAME_DECODED));
  }

  @Test
  void decodeUsername_Should_ReturnDecodedUsername_WhenDecodedUsernameIsGiven() {
    String decodedUsername = new UsernameDecoder().decodeUsername(USERNAME_DECODED);

    assertThat(decodedUsername, is(USERNAME_DECODED));
  }

  @Test
  void decodeUsername_Should_throwIllegalArgumentException_WhenDecodedUsernameIsNull() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          String decodedUsername = new UsernameDecoder().decodeUsername(null);
        });
  }
}
