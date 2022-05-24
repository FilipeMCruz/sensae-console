package pt.sensae.services.rule.management.domain;

import java.util.Objects;

public record RuleScenario(RuleScenarioId id, RuleScenarioContent content, RuleScenarioOwners owners,
                           RuleApplied isApplied, RuleDeleted isDeleted) {

    public RuleScenario withContent(RuleScenarioContent content) {
        return new RuleScenario(id, content, owners, isApplied, isDeleted);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RuleScenario that = (RuleScenario) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
