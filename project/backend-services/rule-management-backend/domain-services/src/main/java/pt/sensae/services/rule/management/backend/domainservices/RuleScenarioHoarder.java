package pt.sensae.services.rule.management.backend.domainservices;

import org.springframework.stereotype.Service;
import pt.sensae.services.rule.management.backend.domain.RuleScenario;
import pt.sensae.services.rule.management.backend.domain.RuleScenarioRepository;

@Service
public class RuleScenarioHoarder {

    private final RuleScenarioRepository repository;

    public RuleScenarioHoarder(RuleScenarioRepository repository) {
        this.repository = repository;
    }

    public RuleScenario hoard(RuleScenario scenario) {
        return this.repository.save(scenario);
    }

}
