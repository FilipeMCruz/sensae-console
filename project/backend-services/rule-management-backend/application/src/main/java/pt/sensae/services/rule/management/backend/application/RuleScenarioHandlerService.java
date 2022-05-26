package pt.sensae.services.rule.management.backend.application;

import org.springframework.stereotype.Service;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.annotation.PostConstruct;

@Service
public class RuleScenarioHandlerService {

    private FluxSink<RuleScenarioNotificationGroupDTO> dataStream;

    private ConnectableFlux<RuleScenarioNotificationGroupDTO> dataPublisher;

    @PostConstruct
    public void init() {
        Flux<RuleScenarioNotificationGroupDTO> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Flux<RuleScenarioNotificationGroupDTO> getSinglePublisher() {
        return dataPublisher;
    }

    public void publish(RuleScenarioNotificationGroupDTO notifications) {
        dataStream.next(notifications);
    }
}