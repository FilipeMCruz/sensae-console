package pt.sensae.services.alert.dispatcher.backend.infrastructure.endpoint.amqp.internal.model;

import pt.sensae.services.alert.dispatcher.backend.application.RuleScenarioNotificationGroupDTO;

import java.util.Set;

public class RuleScenarioNotificationGroupDTOImpl implements RuleScenarioNotificationGroupDTO {

    public Set<RuleScenarioNotificationDTOImpl> notifications;
}
