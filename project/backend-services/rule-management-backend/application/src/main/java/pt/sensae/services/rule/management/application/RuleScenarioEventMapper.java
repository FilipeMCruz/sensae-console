package pt.sensae.services.rule.management.application;

import pt.sensae.services.rule.management.domain.RuleScenario;
import pt.sensae.services.rule.management.domain.RuleScenarioId;

public interface RuleScenarioEventMapper {

    RuleScenarioNotificationDTO domainToUpdatedDto(RuleScenario domain);

    RuleScenarioNotificationDTO domainToDeletedDto(RuleScenarioId domain);
}
