package pt.sensae.services.rule.management.infrastructure.persistence.postgres.mapper;

import pt.sensae.services.rule.management.domain.RuleScenario;
import pt.sensae.services.rule.management.domain.RuleScenarioId;
import pt.sensae.services.rule.management.domain.RuleScenarioContent;
import pt.sensae.services.rule.management.domain.RuleScenarioOwners;
import pt.sensae.services.rule.management.infrastructure.persistence.postgres.model.RuleScenarioPostgres;

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
        return new RuleScenario(scenarioId, content, domains);
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
        return ruleScenario;
    }
}
