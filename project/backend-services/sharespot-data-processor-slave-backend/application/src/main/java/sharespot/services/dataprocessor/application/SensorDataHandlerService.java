package sharespot.services.dataprocessor.application;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.model.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.routing.MessageConsumed;
import pt.sharespot.iot.core.sensor.routing.MessageSupplied;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import sharespot.services.dataprocessor.domain.SensorTypeId;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class SensorDataHandlerService {

    private final SensorDataMapper mapper;

    private FluxSink<MessageSupplied<ProcessedSensorDataDTO>> dataStream;

    private ConnectableFlux<MessageSupplied<ProcessedSensorDataDTO>> dataPublisher;

    private final RoutingKeysProvider provider;

    public SensorDataHandlerService(SensorDataMapper mapper, RoutingKeysProvider provider) {
        this.mapper = mapper;
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

    public void publish(MessageConsumed<ObjectNode> message) {
        message.toSupplied(this::inToOutData, this::inToOutKeys).ifPresent(dataStream::next);
    }

    private Optional<ProcessedSensorDataDTO> inToOutData(ObjectNode node, SensorRoutingKeys keys) {
        return mapper.inToOut(node, SensorTypeId.of(keys.sensorTypeId));
    }

    private Optional<SensorRoutingKeys> inToOutKeys(ProcessedSensorDataDTO data, SensorRoutingKeys keys) {
        return provider.getBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withUpdated(data)
                .from(keys);
    }
}
