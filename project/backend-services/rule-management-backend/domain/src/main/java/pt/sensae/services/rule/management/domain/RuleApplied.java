package pt.sensae.services.rule.management.domain;

public record RuleApplied(boolean value) {

    public static RuleApplied getInstance() {
        return new RuleApplied(false);
    }
}
