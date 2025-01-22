package de.caritas.cob.videoservice.api.service.liveevent;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import de.caritas.cob.videoservice.liveservice.generated.web.LiveControllerApi;
import de.caritas.cob.videoservice.liveservice.generated.web.model.LiveEventMessage;
import java.util.List;
import java.util.stream.Collectors;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LiveEventNotificationServiceTest {

  @InjectMocks private LiveEventNotificationService liveEventNotificationService;
  @Mock private LiveControllerApi liveControllerApi;

  private EasyRandom easyRandom;

  @BeforeEach
  public void setUp() {
    easyRandom = new EasyRandom();
  }

  @Test
  void sendVideoCallRequestLiveEvent_Should_SendLiveEvent() {
    LiveEventMessage liveEventMessage = mock(LiveEventMessage.class);
    List<String> userIds = easyRandom.objects(String.class, 20).collect(Collectors.toList());

    liveEventNotificationService.sendVideoCallRequestLiveEvent(liveEventMessage, userIds);

    verify(liveControllerApi, times(1)).sendLiveEvent(liveEventMessage.userIds(userIds));
  }
}
