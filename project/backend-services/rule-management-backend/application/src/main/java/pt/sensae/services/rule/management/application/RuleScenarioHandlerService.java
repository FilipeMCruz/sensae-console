package pt.sensae.services.rule.management.application;

import org.springframework.stereotype.Service;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import pt.sensae.services.rule.management.domain.RuleScenario;
import pt.sensae.services.rule.management.domain.RuleScenarioId;

import javax.annotation.PostConstruct;

@Service
public class RuleScenarioHandlerService {

    private FluxSink<RuleScenarioNotificationDTO> dataStream;

    private ConnectableFlux<RuleScenarioNotificationDTO> dataPublisher;

    private final RuleScenarioEventMapper mapper;

    public RuleScenarioHandlerService(RuleScenarioEventMapper mapper) {
        this.mapper = mapper;
    }

    @PostConstruct
    public void init() {
        Flux<RuleScenarioNotificationDTO> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Flux<RuleScenarioNotificationDTO> getSinglePublisher() {
        return dataPublisher;
    }

    public void publishUpdate(RuleScenario domain) {
        var outDTO = mapper.domainToUpdatedDto(domain);
        dataStream.next(outDTO);
    }

    public void publishDelete(RuleScenarioId domain) {
        var outDTO = mapper.domainToDeletedDto(domain);
        dataStream.next(outDTO);
    }
}
