package pt.sensae.services.rule.management.domain;

import java.util.Set;
import java.util.UUID;

public record RuleScenarioOwners(Set<UUID> domains) {
    public static RuleScenarioOwners of(Set<UUID> domains) {
        return new RuleScenarioOwners(domains);
    }
}
