package pt.sensae.services.rule.management.backend.domainservices;

import org.springframework.stereotype.Service;
import pt.sensae.services.rule.management.backend.domain.RuleScenario;
import pt.sensae.services.rule.management.backend.domain.RuleScenarioOwners;
import pt.sensae.services.rule.management.backend.domain.RuleScenarioRepository;

import java.util.stream.Stream;

@Service
public class RuleScenarioCollector {

    private final RuleScenarioRepository repository;

    public RuleScenarioCollector(RuleScenarioRepository repository) {
        this.repository = repository;
    }

    public Stream<RuleScenario> collect() {
        return repository.findAll();
    }

    public Stream<RuleScenario> collect(RuleScenarioOwners owners) {
        return repository.findOwned(owners);
    }

    public Stream<RuleScenario> collectDrafts() {
        return repository.findDrafts();
    }
}
