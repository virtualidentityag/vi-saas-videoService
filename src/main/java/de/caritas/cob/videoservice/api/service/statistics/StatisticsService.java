package de.caritas.cob.videoservice.api.service.statistics;

import de.caritas.cob.videoservice.api.service.LogService;
import de.caritas.cob.videoservice.api.service.statistics.event.StatisticsEvent;
import jakarta.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/** Service class for the processing of statistical events. */
@Service
@RequiredArgsConstructor
public class StatisticsService {

  private final @NotNull AmqpTemplate amqpTemplate;

  @Value("${statistics.enabled}")
  private boolean statisticsEnabled;

  @Value("${statistics.rabbitmq.exchange.name}")
  private String rabbitMqExchangeName;

  /**
   * Entry point to write statistics event data to the statistics queue.
   *
   * @param statisticsEvent the concrete {@link StatisticsEvent}
   */
  @Async
  public void fireEvent(StatisticsEvent statisticsEvent) {

    if (statisticsEnabled) {
      statisticsEvent
          .getPayload()
          .ifPresentOrElse(
              payload ->
                  amqpTemplate.convertAndSend(
                      rabbitMqExchangeName,
                      statisticsEvent.getEventType().toString(),
                      MessageBuilder.withBody(payload.getBytes(StandardCharsets.UTF_8))
                          .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                          .build()),
              () ->
                  LogService.logStatisticsEventWarning(
                      "Empty statistics event message payload for type %s received"
                          .formatted(statisticsEvent.getClass().getSimpleName())));
    }
  }
}
