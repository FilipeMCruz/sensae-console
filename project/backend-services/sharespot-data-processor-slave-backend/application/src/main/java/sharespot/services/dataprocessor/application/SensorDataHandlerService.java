package sharespot.services.dataprocessor.application;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.routing.MessageConsumed;
import pt.sharespot.iot.core.routing.MessageSupplied;
import pt.sharespot.iot.core.routing.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.routing.keys.RoutingKeysFactory;
import pt.sharespot.iot.core.sensor.SensorDataDTO;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import sharespot.services.dataprocessor.domain.SensorTypeId;

import javax.annotation.PostConstruct;

@Service
public class SensorDataHandlerService {

    private final SensorDataMapper mapper;

    private FluxSink<MessageSupplied<SensorDataDTO>> dataStream;

    private ConnectableFlux<MessageSupplied<SensorDataDTO>> dataPublisher;

    private final RoutingKeysFactory factory;

    public SensorDataHandlerService(SensorDataMapper mapper, RoutingKeysFactory factory) {
        this.mapper = mapper;
        this.factory = factory;
    }

    @PostConstruct
    public void init() {
        Flux<MessageSupplied<SensorDataDTO>> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Flux<MessageSupplied<SensorDataDTO>> getSinglePublisher() {
        return dataPublisher;
    }

    public void publish(MessageConsumed<ObjectNode> message) {
        mapper.inToOut(message.data, SensorTypeId.of(message.routingKeys.sensorTypeId))
                .ifPresent(dto -> factory.getBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                        .keepSensorTypeId()
                        .keepChannel()
                        .withUpdated(dto)
                        .from(message.routingKeys)
                        .ifPresent(keys -> dataStream.next(new MessageSupplied<>(keys, dto))));
    }
}
