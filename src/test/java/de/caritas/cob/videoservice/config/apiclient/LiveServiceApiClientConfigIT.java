package de.caritas.cob.videoservice.config.apiclient;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import de.caritas.cob.videoservice.VideoServiceApplication;
import de.caritas.cob.videoservice.liveservice.generated.web.LiveControllerApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = VideoServiceApplication.class)
@TestPropertySource(properties = "spring.profiles.active=testing")
class LiveServiceApiClientConfigIT {

  @Autowired private LiveControllerApi liveControllerApi;

  @Value("${live.service.api.url}")
  private String liveServiceApiUrl;

  @Test
  void configureLiveControllerApi_Should_setCorrectApiUrl() {
    String apiClientUrl = this.liveControllerApi.getApiClient().getBasePath();

    assertThat(apiClientUrl, is(this.liveServiceApiUrl));
  }
}
