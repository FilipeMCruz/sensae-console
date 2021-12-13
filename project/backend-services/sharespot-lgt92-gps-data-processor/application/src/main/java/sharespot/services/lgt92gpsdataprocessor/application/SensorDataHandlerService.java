package sharespot.services.lgt92gpsdataprocessor.application;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import sharespot.services.lgt92gpsdataprocessor.application.model.MessageConsumed;

import javax.annotation.PostConstruct;

@Service
public class SensorDataHandlerService {

    private final SensorDataMapper mapper;

    private FluxSink<OutSensorDataDTO> dataStream;

    private ConnectableFlux<OutSensorDataDTO> dataPublisher;

    public SensorDataHandlerService(SensorDataMapper mapper) {
        this.mapper = mapper;
    }

    @PostConstruct
    public void init() {
        Flux<OutSensorDataDTO> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Flux<OutSensorDataDTO> getSinglePublisher() {
        return dataPublisher;
    }

    public void publish(MessageConsumed<ObjectNode> message) {
        mapper.inToOut(message.data)
                .ifPresent(outSensorDataDTO -> dataStream.next(outSensorDataDTO));
    }
}
