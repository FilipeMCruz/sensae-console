package pt.sensae.services.rule.management.backend.infrastructure.endpoint.amqp.model;


import pt.sensae.services.rule.management.backend.application.RuleScenarioNotificationDTO;

public class RuleScenarioNotificationDTOImpl implements RuleScenarioNotificationDTO {

    public String scenarioId;

    public RuleScenarioNotificationTypeDTOImpl notificationType;

    public RuleScenarioDTOImpl information;
}
