package pt.sensae.services.alert.dispatcher.backend.application;


import pt.sensae.services.alert.dispatcher.backend.domain.RuleScenario;

import java.util.stream.Stream;

public interface RuleScenarioEventMapper {

    Stream<RuleScenario> domainToDto(RuleScenarioNotificationGroupDTO notifications);
}
