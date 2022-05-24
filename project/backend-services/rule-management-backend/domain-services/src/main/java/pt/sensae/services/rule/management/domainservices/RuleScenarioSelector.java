package pt.sensae.services.rule.management.domainservices;

import org.springframework.stereotype.Service;
import pt.sensae.services.rule.management.domain.RuleScenario;
import pt.sensae.services.rule.management.domain.RuleScenarioId;
import pt.sensae.services.rule.management.domain.RuleScenarioRepository;

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
