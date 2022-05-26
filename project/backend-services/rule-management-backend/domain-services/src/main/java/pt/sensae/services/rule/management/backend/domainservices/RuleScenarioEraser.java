package pt.sensae.services.rule.management.backend.domainservices;

import org.springframework.stereotype.Service;
import pt.sensae.services.rule.management.backend.domain.RuleScenarioRepository;
import pt.sensae.services.rule.management.backend.domain.RuleScenarioId;

@Service
public class RuleScenarioEraser {

    private final RuleScenarioRepository repository;

    public RuleScenarioEraser(RuleScenarioRepository repository) {
        this.repository = repository;
    }

    public RuleScenarioId erase(RuleScenarioId scenarioId) {
        return repository.delete(scenarioId);
    }
}
