package pt.sensae.services.rule.management.backend.infrastructure.endpoint.amqp.mapper;

import org.springframework.stereotype.Component;
import pt.sensae.services.rule.management.backend.application.RuleScenarioNotificationDTO;
import pt.sensae.services.rule.management.backend.application.RuleScenarioEventMapper;
import pt.sensae.services.rule.management.backend.application.RuleScenarioNotificationGroupDTO;
import pt.sensae.services.rule.management.backend.domain.RuleScenario;
import pt.sensae.services.rule.management.backend.domain.RuleScenarioId;
import pt.sensae.services.rule.management.backend.infrastructure.endpoint.amqp.model.RuleScenarioDTOImpl;
import pt.sensae.services.rule.management.backend.infrastructure.endpoint.amqp.model.RuleScenarioNotificationDTOImpl;
import pt.sensae.services.rule.management.backend.infrastructure.endpoint.amqp.model.RuleScenarioNotificationGroupDTOImpl;
import pt.sensae.services.rule.management.backend.infrastructure.endpoint.amqp.model.RuleScenarioNotificationTypeDTOImpl;

import java.util.Set;

@Component
public class RuleScenarioEventMapperImpl implements RuleScenarioEventMapper {

    @Override
    public RuleScenarioNotificationDTO domainToUpdatedDto(RuleScenario domain) {
        var dto = new RuleScenarioNotificationDTOImpl();
        dto.notificationType = RuleScenarioNotificationTypeDTOImpl.UPDATE;
        dto.scenarioId = domain.id().getValue();
        var info = new RuleScenarioDTOImpl();
        info.content = domain.content().value();
        dto.information = info;
        return dto;
    }

    @Override
    public RuleScenarioNotificationDTO domainToDeletedDto(RuleScenarioId domain) {
        var dto = new RuleScenarioNotificationDTOImpl();
        dto.notificationType = RuleScenarioNotificationTypeDTOImpl.DELETE;
        dto.scenarioId = domain.getValue();
        return dto;
    }

    @Override
    public RuleScenarioNotificationGroupDTO domainToDto(Set<RuleScenarioNotificationDTO> notifications) {
        return RuleScenarioNotificationGroupDTOImpl.of(notifications);
    }
}
