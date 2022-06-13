package pt.sensae.services.rule.management.backend.infrastructure.endpoint.amqp.model;

import pt.sensae.services.rule.management.backend.application.RuleScenarioNotificationDTO;
import pt.sensae.services.rule.management.backend.application.RuleScenarioNotificationGroupDTO;

import java.util.Set;
import java.util.stream.Collectors;

public class RuleScenarioNotificationGroupDTOImpl implements RuleScenarioNotificationGroupDTO {

    public boolean staleData;

    public Set<RuleScenarioNotificationDTOImpl> notifications;

    public static RuleScenarioNotificationGroupDTOImpl of(Set<RuleScenarioNotificationDTO> notifications, boolean staleData) {
        var ruleScenarioNotificationGroupDTO = new RuleScenarioNotificationGroupDTOImpl();
        ruleScenarioNotificationGroupDTO.notifications = notifications.stream()
                .map(s -> (RuleScenarioNotificationDTOImpl) s)
                .collect(Collectors.toSet());
        ruleScenarioNotificationGroupDTO.staleData = staleData;
        return ruleScenarioNotificationGroupDTO;
    }

    @Override
    public boolean isStale() {
        return staleData;
    }
}
