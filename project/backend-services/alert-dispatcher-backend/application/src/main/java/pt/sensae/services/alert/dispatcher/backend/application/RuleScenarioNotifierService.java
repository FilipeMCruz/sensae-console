package pt.sensae.services.alert.dispatcher.backend.application;

import org.springframework.stereotype.Service;

@Service
public class RuleScenarioNotifierService {

    private final RulesContainer container;

    private final RuleScenarioEventMapper mapper;

    public RuleScenarioNotifierService(RulesContainer container, RuleScenarioEventMapper mapper) {
        this.container = container;
        this.mapper = mapper;
    }

    public void info(RuleScenarioNotificationGroupDTO dto) {
        var rules = mapper.domainToDto(dto);
        container.updateEnvironment(rules);
    }
}
