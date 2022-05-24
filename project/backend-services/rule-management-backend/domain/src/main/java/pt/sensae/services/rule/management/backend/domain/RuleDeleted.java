package pt.sensae.services.rule.management.backend.domain;

public record RuleDeleted(boolean value) {

    public static RuleDeleted getInstance() {
        return new RuleDeleted(false);
    }
}
