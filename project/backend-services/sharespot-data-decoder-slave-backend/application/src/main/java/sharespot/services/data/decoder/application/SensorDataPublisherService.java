package sharespot.services.data.decoder.application;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.keys.MessageConsumed;
import pt.sharespot.iot.core.keys.MessageSupplied;
import pt.sharespot.iot.core.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import sharespot.services.data.decoder.domain.SensorTypeId;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class SensorDataPublisherService {

    private final DataDecoderExecutor mapper;

    private FluxSink<MessageSupplied<SensorDataDTO, SensorRoutingKeys>> dataStream;

    private ConnectableFlux<MessageSupplied<SensorDataDTO, SensorRoutingKeys>> dataPublisher;

    private final RoutingKeysProvider provider;

    public SensorDataPublisherService(DataDecoderExecutor mapper, RoutingKeysProvider provider) {
        this.mapper = mapper;
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

    public void publish(MessageConsumed<ObjectNode, SensorRoutingKeys> message) {
        message.toSupplied(this::inToOutData, this::inToOutKeys).ifPresent(dataStream::next);
    }

    private Optional<SensorDataDTO> inToOutData(ObjectNode node, SensorRoutingKeys keys) {
        return mapper.decodeData(node, SensorTypeId.of(keys.sensorTypeId));
    }

    private Optional<SensorRoutingKeys> inToOutKeys(SensorDataDTO data, SensorRoutingKeys keys) {
        return provider.getBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withUpdated(data)
                .from(keys);
    }
}
