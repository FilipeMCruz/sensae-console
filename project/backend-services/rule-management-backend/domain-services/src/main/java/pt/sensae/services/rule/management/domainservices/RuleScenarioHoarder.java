package pt.sensae.services.rule.management.domainservices;

import org.springframework.stereotype.Service;
import pt.sensae.services.rule.management.domain.RuleScenario;
import pt.sensae.services.rule.management.domain.RuleScenarioId;
import pt.sensae.services.rule.management.domain.RuleScenarioRepository;

import java.util.Optional;

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
