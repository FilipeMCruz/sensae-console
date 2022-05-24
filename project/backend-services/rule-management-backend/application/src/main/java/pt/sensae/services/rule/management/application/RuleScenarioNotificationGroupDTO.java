package pt.sensae.services.rule.management.application;

import java.util.Set;

public class RuleScenarioNotificationGroupDTO {

    public Set<RuleScenarioNotificationDTO> notifications;

    public static RuleScenarioNotificationGroupDTO of(Set<RuleScenarioNotificationDTO> notifications) {
        var ruleScenarioNotificationGroupDTO = new RuleScenarioNotificationGroupDTO();
        ruleScenarioNotificationGroupDTO.notifications = notifications;
        return ruleScenarioNotificationGroupDTO;
    }

}
