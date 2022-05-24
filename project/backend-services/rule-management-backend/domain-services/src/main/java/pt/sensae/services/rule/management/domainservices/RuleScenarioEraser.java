package pt.sensae.services.rule.management.domainservices;

import org.springframework.stereotype.Service;
import pt.sensae.services.rule.management.domain.RuleScenarioRepository;
import pt.sensae.services.rule.management.domain.RuleScenarioId;

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
