package de.caritas.cob.videoservice.api.service.video;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import de.caritas.cob.videoservice.api.exception.httpresponse.InternalServerErrorException;
import de.caritas.cob.videoservice.api.service.video.jwt.TokenGeneratorService;
import de.caritas.cob.videoservice.api.service.video.jwt.model.VideoCallToken;
import de.caritas.cob.videoservice.api.service.video.jwt.model.VideoCallUrls;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VideoCallUrlGeneratorServiceTest {

  private static final String FIELD_NAME_VIDEO_CALL_URL = "videoCallServerUrl";
  private static final String VIDEO_CALL_URL = "https://video.call";

  @InjectMocks private VideoCallUrlGeneratorService videoCallUrlGeneratorService;

  @Mock private TokenGeneratorService tokenGeneratorService;

  @Test
  void generateVideoCallUrls_Should_generateExpectedVideoCallUrls_When_askerNameIsGiven() {
    setField(this.videoCallUrlGeneratorService, FIELD_NAME_VIDEO_CALL_URL, VIDEO_CALL_URL);
    VideoCallToken videoCallToken = new EasyRandom().nextObject(VideoCallToken.class);
    String moderatorToken = "moderatorToken";
    when(this.tokenGeneratorService.generateNonModeratorVideoCallToken(any()))
        .thenReturn(videoCallToken);
    when(this.tokenGeneratorService.generateModeratorToken(any(), any()))
        .thenReturn(moderatorToken);

    VideoCallUrls videoCallUrls =
        this.videoCallUrlGeneratorService.generateVideoCallUrls("uniqueId");

    assertThat(
        videoCallUrls.getUserVideoUrl(),
        is(VIDEO_CALL_URL + "/uniqueId?jwt=" + videoCallToken.getUserRelatedToken()));
    assertThat(
        videoCallUrls.getModeratorVideoUrl(),
        is(VIDEO_CALL_URL + "/uniqueId?jwt=" + moderatorToken));
  }

  @Test
  void
      generateVideoCallUrls_Should_throwInternalServerErrorException_When_videoUrlIsInvalid() {
    assertThrows(
        InternalServerErrorException.class,
        () -> {
          VideoCallToken videoCallToken = new EasyRandom().nextObject(VideoCallToken.class);
          when(this.tokenGeneratorService.generateNonModeratorVideoCallToken(any()))
              .thenReturn(videoCallToken);

          this.videoCallUrlGeneratorService.generateVideoCallUrls("uniqueId");
        });
  }
}
