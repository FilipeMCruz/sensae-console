package pt.sensae.services.rule.management.backend.infrastructure.endpoint.graphql.model;

import pt.sensae.services.rule.management.backend.application.RuleScenarioDTO;

public class RuleScenarioDTOImpl implements RuleScenarioDTO {
    public RuleScenarioIdDTOImpl id;
    public String content;

    public boolean applied;
}
