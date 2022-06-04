package pt.sensae.services.rule.management.backend.domain;

import pt.sensae.services.rule.management.backend.domain.exceptions.InvalidScenarioException;

import java.util.Objects;

public class RuleScenarioId {

    private final String value;

    private RuleScenarioId(String value) {
        if (value.trim().isBlank() || !value.matches("^[#0-9A-Za-z -]+$"))
            throw new InvalidScenarioException("Invalid Rule Scenario Id\nAllowed Characters: Letters, Numbers, # spaces and -");
        this.value = value;
    }

    public static RuleScenarioId of(String value) {
        return new RuleScenarioId(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RuleScenarioId that)) return false;

        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
