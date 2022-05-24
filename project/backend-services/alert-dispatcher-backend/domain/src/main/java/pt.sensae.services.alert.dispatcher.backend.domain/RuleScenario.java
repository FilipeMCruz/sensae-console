package pt.sensae.services.alert.dispatcher.backend.domain;

import java.util.Objects;

public record RuleScenario(RuleScenarioId id, RuleScenarioContent content,
                           RuleScenarioNotificationType notificationType) {

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
