package pt.sensae.services.rule.management.application;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pt.sensae.services.rule.management.domainservices.RuleScenarioCollector;

import java.util.Set;
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

    @Scheduled(cron = "0 0/30 * * * * ?")
    public void publishDrafts() {
        var drafts = this.collector.collectDrafts().collect(Collectors.toSet());
        var notifications = drafts.stream().map(draft -> {
            if (draft.isDeleted().value()) {
                return mapper.domainToDeletedDto(draft.id());
            } else {
                return mapper.domainToUpdatedDto(draft);
            }
        }).collect(Collectors.toSet());
        publisher.publish(RuleScenarioNotificationGroupDTO.of(notifications));
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        var collect = this.collector.collect()
                .filter(scenario -> scenario.isApplied().value())
                .map(mapper::domainToUpdatedDto)
                .collect(Collectors.toSet());
        publisher.publish(RuleScenarioNotificationGroupDTO.of(collect));
    }
}
