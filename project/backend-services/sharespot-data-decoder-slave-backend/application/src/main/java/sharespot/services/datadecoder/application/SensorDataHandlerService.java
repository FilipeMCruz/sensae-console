package sharespot.services.datadecoder.application;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.routing.MessageConsumed;
import pt.sharespot.iot.core.routing.MessageSupplied;
import pt.sharespot.iot.core.routing.keys.RoutingKeys;
import pt.sharespot.iot.core.routing.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.SensorDataDTO;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import sharespot.services.datadecoder.domain.SensorTypeId;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class SensorDataHandlerService {

    private final SensorDataMapper mapper;

    private FluxSink<MessageSupplied<SensorDataDTO>> dataStream;

    private ConnectableFlux<MessageSupplied<SensorDataDTO>> dataPublisher;

    private final RoutingKeysProvider provider;

    public SensorDataHandlerService(SensorDataMapper mapper, RoutingKeysProvider provider) {
        this.mapper = mapper;
        this.provider = provider;
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
        message.toSupplied(this::inToOutData, this::inToOutKeys).ifPresent(dataStream::next);
    }

    private Optional<SensorDataDTO> inToOutData(ObjectNode node, RoutingKeys keys) {
        return mapper.inToOut(node, SensorTypeId.of(keys.sensorTypeId));
    }

    private Optional<RoutingKeys> inToOutKeys(SensorDataDTO data, RoutingKeys keys) {
        return provider.getBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withUpdated(data)
                .from(keys);
    }
}
