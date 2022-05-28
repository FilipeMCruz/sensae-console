package pt.sensae.services.rule.management.backend.application;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pt.sensae.services.rule.management.backend.domainservices.RuleScenarioCollector;

import java.util.stream.Collectors;


@Service
public class RuleScenarioDraftApplierService {

    private final RuleScenarioCollector collector;

    private final RuleScenarioHandlerService publisher;

    private final RuleScenarioEventMapper mapper;

    public RuleScenarioDraftApplierService(RuleScenarioCollector collector, RuleScenarioHandlerService publisher, RuleScenarioEventMapper mapper) {
        this.collector = collector;
        this.publisher = publisher;
        this.mapper = mapper;
    }

    @Scheduled(cron = "0 0/30 * * * ?")
    public void publishDrafts() {
        var notifications = this.collector.collectDrafts()
                .map(draft -> draft.isDeleted().value() ?
                        mapper.domainToDeletedDto(draft.id()) :
                        mapper.domainToUpdatedDto(draft))
                .collect(Collectors.toSet());

        if (!notifications.isEmpty()) publisher.publish(mapper.domainToDto(notifications, false));
    }

    public void publishCurrentState() {
        var notifications = this.collector.collect()
                .filter(scenario -> scenario.isApplied().value())
                .map(mapper::domainToUpdatedDto)
                .collect(Collectors.toSet());

        if (!notifications.isEmpty()) publisher.publish(mapper.domainToDto(notifications, true));
    }
}
