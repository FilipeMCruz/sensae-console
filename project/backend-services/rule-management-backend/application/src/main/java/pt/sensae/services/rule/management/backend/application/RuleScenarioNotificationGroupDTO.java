package pt.sensae.services.rule.management.backend.application;

import java.util.Set;

public class RuleScenarioNotificationGroupDTO {

    public Set<RuleScenarioNotificationDTO> notifications;

    public static RuleScenarioNotificationGroupDTO of(Set<RuleScenarioNotificationDTO> notifications) {
        var ruleScenarioNotificationGroupDTO = new RuleScenarioNotificationGroupDTO();
        ruleScenarioNotificationGroupDTO.notifications = notifications;
        return ruleScenarioNotificationGroupDTO;
    }

}
