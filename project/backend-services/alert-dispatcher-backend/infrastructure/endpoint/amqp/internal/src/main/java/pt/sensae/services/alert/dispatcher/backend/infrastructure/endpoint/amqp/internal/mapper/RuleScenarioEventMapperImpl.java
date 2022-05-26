package pt.sensae.services.alert.dispatcher.backend.infrastructure.endpoint.amqp.internal.mapper;

import org.springframework.stereotype.Component;
import pt.sensae.services.alert.dispatcher.backend.application.RuleScenarioEventMapper;
import pt.sensae.services.alert.dispatcher.backend.application.RuleScenarioNotificationGroupDTO;
import pt.sensae.services.alert.dispatcher.backend.domain.RuleScenario;
import pt.sensae.services.alert.dispatcher.backend.domain.RuleScenarioContent;
import pt.sensae.services.alert.dispatcher.backend.domain.RuleScenarioId;
import pt.sensae.services.alert.dispatcher.backend.domain.RuleScenarioNotificationType;
import pt.sensae.services.alert.dispatcher.backend.infrastructure.endpoint.amqp.internal.model.RuleScenarioNotificationGroupDTOImpl;
import pt.sensae.services.alert.dispatcher.backend.infrastructure.endpoint.amqp.internal.model.RuleScenarioNotificationTypeDTOImpl;

import java.util.stream.Stream;


@Component
public class RuleScenarioEventMapperImpl implements RuleScenarioEventMapper {

    @Override
    public Stream<RuleScenario> domainToDto(RuleScenarioNotificationGroupDTO notifications) {
        var dto = (RuleScenarioNotificationGroupDTOImpl) notifications;
        return dto.notifications.stream().map(notificationDTO -> {
            if (notificationDTO.notificationType.equals(RuleScenarioNotificationTypeDTOImpl.DELETE)) {
                return new RuleScenario(RuleScenarioId.of(notificationDTO.scenarioId), RuleScenarioContent.of(""), RuleScenarioNotificationType.DELETE);
            } else {
                return new RuleScenario(RuleScenarioId.of(notificationDTO.scenarioId), RuleScenarioContent.of(notificationDTO.information.content), RuleScenarioNotificationType.UPDATE);
            }
        });
    }
}
