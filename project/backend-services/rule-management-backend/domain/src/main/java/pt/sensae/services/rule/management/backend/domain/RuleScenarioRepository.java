package pt.sensae.services.rule.management.backend.domain;

import java.util.Optional;
import java.util.stream.Stream;

public interface RuleScenarioRepository {

    RuleScenario save(RuleScenario records);

    Stream<RuleScenario> findAll();

    RuleScenarioId delete(RuleScenarioId id);

    Optional<RuleScenario> findById(RuleScenarioId id);

    Stream<RuleScenario> findOwned(RuleScenarioOwners owners);

    Stream<RuleScenario> findDrafts();
}
