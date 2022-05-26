package pt.sensae.services.alert.dispatcher.backend.domain;

public record RuleScenarioContent(String value) {

    public static RuleScenarioContent of(String content) {
        return new RuleScenarioContent(content);
    }
}
