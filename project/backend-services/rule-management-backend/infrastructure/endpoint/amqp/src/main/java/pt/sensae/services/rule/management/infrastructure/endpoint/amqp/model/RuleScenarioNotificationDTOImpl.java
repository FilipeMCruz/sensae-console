package pt.sensae.services.rule.management.infrastructure.endpoint.amqp.model;


import pt.sensae.services.rule.management.application.RuleScenarioNotificationDTO;

public class RuleScenarioNotificationDTOImpl implements RuleScenarioNotificationDTO {

    public String scenarioId;

    public RuleScenarioNotificationTypeDTOImpl notificationType;

    public RuleScenarioDTOImpl information;
}
