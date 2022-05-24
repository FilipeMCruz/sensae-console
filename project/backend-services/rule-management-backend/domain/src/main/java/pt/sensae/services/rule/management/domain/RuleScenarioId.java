package pt.sensae.services.rule.management.domain;

import java.util.Objects;

public class RuleScenarioId {

    private final String value;

    private RuleScenarioId(String value) {
        this.value = value;
    }

    public boolean same(RuleScenarioId id) {
        return id.value.equals(value);
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
