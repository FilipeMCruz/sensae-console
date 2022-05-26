package pt.sensae.services.data.validator.backend.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import pt.sharespot.iot.core.sensor.routing.MessageConsumed;
import pt.sharespot.iot.core.sensor.routing.MessageSupplied;
import pt.sharespot.iot.core.sensor.routing.keys.SensorRoutingKeys;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class SensorDataHandlerService {

    private FluxSink<MessageSupplied<SensorDataDTO>> dataStream;

    private ConnectableFlux<MessageSupplied<SensorDataDTO>> dataPublisher;

    private final DataValidatorService service;

    public SensorDataHandlerService(DataValidatorService service) {
        this.service = service;
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

    public void publish(MessageConsumed<SensorDataDTO> message) {
        message.toSupplied(this::inToOutData, this::inToOutKeys).ifPresent(dataStream::next);
    }

    private Optional<SensorDataDTO> inToOutData(SensorDataDTO node, SensorRoutingKeys keys) {
        return Optional.of(node);
    }

    private Optional<SensorRoutingKeys> inToOutKeys(SensorDataDTO data, SensorRoutingKeys keys) {
        return service.decide(data, keys);
    }
}
