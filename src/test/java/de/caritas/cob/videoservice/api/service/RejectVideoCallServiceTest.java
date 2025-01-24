package de.caritas.cob.videoservice.api.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.caritas.cob.videoservice.api.model.RejectVideoCallDTO;
import de.caritas.cob.videoservice.api.service.httpheader.SecurityHeaderSupplier;
import de.caritas.cob.videoservice.api.service.httpheader.TenantHeaderSupplier;
import de.caritas.cob.videoservice.messageservice.generated.ApiClient;
import de.caritas.cob.videoservice.messageservice.generated.web.MessageControllerApi;
import de.caritas.cob.videoservice.messageservice.generated.web.model.VideoCallMessageDTO;
import de.caritas.cob.videoservice.messageservice.generated.web.model.VideoCallMessageDTO.EventTypeEnum;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;

@ExtendWith(MockitoExtension.class)
class RejectVideoCallServiceTest {

  @InjectMocks private RejectVideoCallService rejectVideoCallService;

  @Mock private MessageControllerApi messageControllerApi;

  @Mock private SecurityHeaderSupplier securityHeaderSupplier;

  @Mock private TenantHeaderSupplier tenantHeaderSupplier;

  @Mock private ApiClient apiClient;

  @Test
  void rejectVideoCall_Should_useServicesCorrectly() {
    var securityHeaders = new HttpHeaders();
    when(this.messageControllerApi.getApiClient()).thenReturn(this.apiClient);
    when(this.securityHeaderSupplier.getKeycloakAndCsrfHttpHeaders()).thenReturn(securityHeaders);
    var rejectVideoCallDto = new EasyRandom().nextObject(RejectVideoCallDTO.class);

    this.rejectVideoCallService.rejectVideoCall(rejectVideoCallDto);

    VideoCallMessageDTO expectedMessage =
        new VideoCallMessageDTO()
            .eventType(EventTypeEnum.IGNORED_CALL)
            .initiatorUserName(rejectVideoCallDto.getInitiatorUsername())
            .initiatorRcUserId(rejectVideoCallDto.getInitiatorRcUserId());
    verify(this.securityHeaderSupplier).getKeycloakAndCsrfHttpHeaders();
    verify(this.tenantHeaderSupplier).addTenantHeader(securityHeaders);
    verify(this.messageControllerApi)
        .createVideoHintMessage(rejectVideoCallDto.getRcGroupId(), expectedMessage);
  }
}
