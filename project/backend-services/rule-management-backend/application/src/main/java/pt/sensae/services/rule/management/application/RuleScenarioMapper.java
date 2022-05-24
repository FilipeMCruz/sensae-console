package pt.sensae.services.rule.management.application;

import pt.sensae.services.rule.management.domain.RuleScenario;
import pt.sensae.services.rule.management.domain.RuleScenarioId;

import java.util.Set;
import java.util.UUID;

public interface RuleScenarioMapper {

    RuleScenario dtoToDomain(RuleScenarioDTO dto, Set<UUID> owners);

    RuleScenarioDTO domainToDto(RuleScenario domain);

    RuleScenarioId dtoToDomain(RuleScenarioIdDTO dto);

    RuleScenarioIdDTO domainToDto(RuleScenarioId domain);

}
