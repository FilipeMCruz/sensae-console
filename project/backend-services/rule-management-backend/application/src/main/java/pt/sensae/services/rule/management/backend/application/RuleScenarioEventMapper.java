package pt.sensae.services.rule.management.backend.application;

import pt.sensae.services.rule.management.backend.domain.RuleScenario;
import pt.sensae.services.rule.management.backend.domain.RuleScenarioId;

import java.util.Set;

public interface RuleScenarioEventMapper {

    RuleScenarioNotificationDTO domainToUpdatedDto(RuleScenario domain);

    RuleScenarioNotificationDTO domainToDeletedDto(RuleScenarioId domain);

    RuleScenarioNotificationGroupDTO domainToDto(Set<RuleScenarioNotificationDTO> notifications, boolean staleData);
}
