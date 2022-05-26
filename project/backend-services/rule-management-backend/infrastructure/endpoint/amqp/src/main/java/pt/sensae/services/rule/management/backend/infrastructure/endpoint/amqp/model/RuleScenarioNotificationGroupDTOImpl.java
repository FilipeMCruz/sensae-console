package pt.sensae.services.rule.management.backend.infrastructure.endpoint.amqp.model;

import pt.sensae.services.rule.management.backend.application.RuleScenarioNotificationDTO;
import pt.sensae.services.rule.management.backend.application.RuleScenarioNotificationGroupDTO;

import java.util.Set;
import java.util.stream.Collectors;

public class RuleScenarioNotificationGroupDTOImpl implements RuleScenarioNotificationGroupDTO {

    public Set<RuleScenarioNotificationDTOImpl> notifications;

    public static RuleScenarioNotificationGroupDTOImpl of(Set<RuleScenarioNotificationDTO> notifications) {
        var ruleScenarioNotificationGroupDTO = new RuleScenarioNotificationGroupDTOImpl();
        ruleScenarioNotificationGroupDTO.notifications = notifications.stream()
                .map(s -> (RuleScenarioNotificationDTOImpl) s)
                .collect(Collectors.toSet());
        return ruleScenarioNotificationGroupDTO;
    }
}
