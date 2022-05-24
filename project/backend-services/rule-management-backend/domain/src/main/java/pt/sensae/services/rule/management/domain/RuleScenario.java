package pt.sensae.services.rule.management.domain;

public record RuleScenario(RuleScenarioId id, RuleScenarioContent content, RuleScenarioOwners owners) {

    public RuleScenario withContent(RuleScenarioContent content) {
        return new RuleScenario(id, content, owners);
    }
}
