package pt.sensae.services.rule.management.backend.application;

import pt.sensae.services.rule.management.backend.domain.RuleScenario;
import pt.sensae.services.rule.management.backend.domain.RuleScenarioId;

public interface RuleScenarioEventMapper {

    RuleScenarioNotificationDTO domainToUpdatedDto(RuleScenario domain);

    RuleScenarioNotificationDTO domainToDeletedDto(RuleScenarioId domain);
}
