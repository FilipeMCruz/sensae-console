package pt.sensae.services.rule.management.domain;

public record RuleDeleted(boolean value) {

    public static RuleDeleted getInstance() {
        return new RuleDeleted(false);
    }
}
