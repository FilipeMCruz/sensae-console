package pt.sensae.services.rule.management.backend.domainservices;

import org.springframework.stereotype.Service;
import pt.sensae.services.rule.management.backend.domain.RuleScenario;
import pt.sensae.services.rule.management.backend.domain.RuleScenarioId;
import pt.sensae.services.rule.management.backend.domain.RuleScenarioRepository;

import java.util.Optional;

@Service
public class RuleScenarioSelector {

    private final RuleScenarioRepository repository;

    public RuleScenarioSelector(RuleScenarioRepository repository) {
        this.repository = repository;
    }

    public Optional<RuleScenario> findById(RuleScenarioId scenario) {
        return this.repository.findById(scenario);
    }
}
