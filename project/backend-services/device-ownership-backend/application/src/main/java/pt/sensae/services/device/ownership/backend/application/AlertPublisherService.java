package pt.sensae.services.device.ownership.backend.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.alert.model.AlertDTO;
import pt.sharespot.iot.core.alert.routing.keys.AlertRoutingKeys;
import pt.sharespot.iot.core.keys.*;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class AlertPublisherService {

    private FluxSink<MessageSupplied<AlertDTO, AlertRoutingKeys>> dataStream;

    private ConnectableFlux<MessageSupplied<AlertDTO, AlertRoutingKeys>> dataPublisher;

    private final DomainAppenderService appender;
    private final RoutingKeysProvider provider;

    public AlertPublisherService(DomainAppenderService appender, RoutingKeysProvider provider) {
        this.appender = appender;
        this.provider = provider;
    }

    @PostConstruct
    public void init() {
        Flux<MessageSupplied<AlertDTO, AlertRoutingKeys>> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Flux<MessageSupplied<AlertDTO, AlertRoutingKeys>> getSinglePublisher() {
        return dataPublisher;
    }

    public void publish(MessageConsumed<AlertDTO, AlertRoutingKeys> message) {
        message.toSupplied(this::inToOutData, this::inToOutKeys).ifPresent(dataStream::next);
    }

    private Optional<AlertDTO> inToOutData(AlertDTO node, AlertRoutingKeys keys) {
        return appender.tryToAppend(node);
    }

    private Optional<AlertRoutingKeys> inToOutKeys(AlertDTO data, AlertRoutingKeys keys) {
        return provider.getAlertTopicBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withOwnershipType(OwnershipOptions.WITH_DOMAIN_OWNERSHIP)
                .withContainerType(ContainerTypeOptions.IDENTITY_MANAGEMENT)
                .from(keys);
    }
}
