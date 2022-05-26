package pt.sensae.services.rule.management.backend.application;

import pt.sensae.services.rule.management.backend.domain.RuleScenario;
import pt.sensae.services.rule.management.backend.domain.RuleScenarioId;

import java.util.Set;
import java.util.UUID;

public interface RuleScenarioMapper {

    RuleScenario dtoToDomain(RuleScenarioDTO dto, Set<UUID> owners);

    RuleScenarioDTO domainToDto(RuleScenario domain);

    RuleScenarioId dtoToDomain(RuleScenarioIdDTO dto);
}
