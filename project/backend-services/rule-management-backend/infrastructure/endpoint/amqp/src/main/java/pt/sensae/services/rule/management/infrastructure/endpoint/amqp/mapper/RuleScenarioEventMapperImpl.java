package pt.sensae.services.rule.management.infrastructure.endpoint.amqp.mapper;

import org.springframework.stereotype.Component;
import pt.sensae.services.rule.management.application.RuleScenarioNotificationDTO;
import pt.sensae.services.rule.management.application.RuleScenarioEventMapper;
import pt.sensae.services.rule.management.domain.RuleScenario;
import pt.sensae.services.rule.management.domain.RuleScenarioId;
import pt.sensae.services.rule.management.infrastructure.endpoint.amqp.model.RuleScenarioDTOImpl;
import pt.sensae.services.rule.management.infrastructure.endpoint.amqp.model.RuleScenarioNotificationDTOImpl;
import pt.sensae.services.rule.management.infrastructure.endpoint.amqp.model.RuleScenarioNotificationTypeDTOImpl;

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
}
