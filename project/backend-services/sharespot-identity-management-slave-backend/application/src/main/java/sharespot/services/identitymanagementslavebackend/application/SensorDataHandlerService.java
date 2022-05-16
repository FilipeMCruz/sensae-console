package sharespot.services.identitymanagementslavebackend.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.model.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.routing.MessageConsumed;
import pt.sharespot.iot.core.sensor.routing.MessageSupplied;
import pt.sharespot.iot.core.sensor.routing.keys.DomainOwnershipOptions;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class SensorDataHandlerService {

    private FluxSink<MessageSupplied<ProcessedSensorDataDTO>> dataStream;

    private ConnectableFlux<MessageSupplied<ProcessedSensorDataDTO>> dataPublisher;

    private final DomainAppenderService appender;
    private final RoutingKeysProvider provider;

    public SensorDataHandlerService(DomainAppenderService appender, RoutingKeysProvider provider) {
        this.appender = appender;
        this.provider = provider;
    }

    @PostConstruct
    public void init() {
        Flux<MessageSupplied<ProcessedSensorDataDTO>> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Flux<MessageSupplied<ProcessedSensorDataDTO>> getSinglePublisher() {
        return dataPublisher;
    }

    public void publish(MessageConsumed<ProcessedSensorDataDTO> message) {
        message.toSupplied(this::inToOutData, this::inToOutKeys).ifPresent(dataStream::next);
    }

    private Optional<ProcessedSensorDataDTO> inToOutData(ProcessedSensorDataDTO node, SensorRoutingKeys keys) {
        return Optional.ofNullable(appender.append(node));
    }

    private Optional<SensorRoutingKeys> inToOutKeys(ProcessedSensorDataDTO data, SensorRoutingKeys keys) {
        return provider.getBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withUpdated(data)
                .withOwnership(DomainOwnershipOptions.WITH_DOMAIN_OWNERSHIP)
                .from(keys);
    }
}
