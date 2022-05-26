package pt.sensae.services.rule.management.backend.infrastructure.persistence.postgres.mapper;

import pt.sensae.services.rule.management.backend.domain.*;
import pt.sensae.services.rule.management.backend.infrastructure.persistence.postgres.model.RuleScenarioPostgres;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

public class RuleScenarioMapper {

    public static RuleScenario postgresToDomain(RuleScenarioPostgres postgres) {
        var scenarioId = RuleScenarioId.of(postgres.scenarioId);
        var content = RuleScenarioContent.of(postgres.scenarioContent);
        var domains = RuleScenarioOwners.of(Arrays.stream(postgres.scenarioOwners)
                .map(UUID::fromString)
                .collect(Collectors.toSet()));
        return new RuleScenario(scenarioId, content, domains, new RuleApplied(postgres.applied), new RuleDeleted(postgres.deleted));
    }

    public static RuleScenarioPostgres domainToPostgres(RuleScenario domain) {
        var ruleScenario = new RuleScenarioPostgres();
        ruleScenario.scenarioId = domain.id().getValue();
        ruleScenario.scenarioContent = domain.content().value();
        ruleScenario.scenarioOwners = domain.owners()
                .domains()
                .stream()
                .map(UUID::toString)
                .toList()
                .toArray(new String[0]);
        ruleScenario.applied = domain.isApplied().value();
        return ruleScenario;
    }
}
