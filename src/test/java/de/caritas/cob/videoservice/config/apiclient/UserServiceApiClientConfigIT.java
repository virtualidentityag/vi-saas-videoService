package de.caritas.cob.videoservice.config.apiclient;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import de.caritas.cob.videoservice.VideoServiceApplication;
import de.caritas.cob.videoservice.userservice.generated.web.UserControllerApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = VideoServiceApplication.class)
@TestPropertySource(properties = "spring.profiles.active=testing")
class UserServiceApiClientConfigIT {

  @Autowired private UserControllerApi userControllerApi;

  @Value("${user.service.api.url}")
  private String userServiceApiUrl;

  @Test
  void configureLiveControllerApi_Should_setCorrectApiUrl() {
    String apiClientUrl = this.userControllerApi.getApiClient().getBasePath();

    assertThat(apiClientUrl, is(this.userServiceApiUrl));
  }
}
