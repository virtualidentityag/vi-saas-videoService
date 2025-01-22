package de.caritas.cob.videoservice.api.config.apiclient;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import de.caritas.cob.videoservice.VideoServiceApplication;
import de.caritas.cob.videoservice.messageservice.generated.web.MessageControllerApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = VideoServiceApplication.class)
@TestPropertySource(properties = "spring.profiles.active=testing")
class MessageServiceApiClientConfigIT {

  @Autowired private MessageControllerApi messageControllerApi;

  @Value("${message.service.api.url}")
  private String serviceApiUrl;

  @Test
  void configureMessageControllerApi_Should_setCorrectApiUrl() {
    String apiClientUrl = this.messageControllerApi.getApiClient().getBasePath();

    assertThat(apiClientUrl, is(this.serviceApiUrl));
  }
}
