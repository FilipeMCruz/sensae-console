package pt.sensae.services.device.ownership.backend.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.keys.MessageConsumed;
import pt.sharespot.iot.core.keys.MessageSupplied;
import pt.sharespot.iot.core.keys.OwnershipOptions;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class SensorDataPublisherService {

    private FluxSink<MessageSupplied<SensorDataDTO, SensorRoutingKeys>> dataStream;

    private ConnectableFlux<MessageSupplied<SensorDataDTO, SensorRoutingKeys>> dataPublisher;

    private final DomainAppenderService appender;
    private final RoutingKeysProvider provider;

    public SensorDataPublisherService(DomainAppenderService appender, RoutingKeysProvider provider) {
        this.appender = appender;
        this.provider = provider;
    }

    @PostConstruct
    public void init() {
        Flux<MessageSupplied<SensorDataDTO, SensorRoutingKeys>> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Flux<MessageSupplied<SensorDataDTO, SensorRoutingKeys>> getSinglePublisher() {
        return dataPublisher;
    }

    public void publish(MessageConsumed<SensorDataDTO, SensorRoutingKeys> message) {
        message.toSupplied(this::inToOutData, this::inToOutKeys).ifPresent(dataStream::next);
    }

    private Optional<SensorDataDTO> inToOutData(SensorDataDTO node, SensorRoutingKeys keys) {
        return appender.tryToAppend(node);
    }

    private Optional<SensorRoutingKeys> inToOutKeys(SensorDataDTO data, SensorRoutingKeys keys) {
        return provider.getBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withUpdated(data)
                .withOwnership(OwnershipOptions.WITH_DOMAIN_OWNERSHIP)
                .from(keys);
    }
}
