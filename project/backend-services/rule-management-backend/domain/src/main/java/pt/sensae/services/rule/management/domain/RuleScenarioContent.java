package pt.sensae.services.rule.management.domain;

public record RuleScenarioContent(String value) {

    public static RuleScenarioContent of(String content) {
        return new RuleScenarioContent(content);
    }
}