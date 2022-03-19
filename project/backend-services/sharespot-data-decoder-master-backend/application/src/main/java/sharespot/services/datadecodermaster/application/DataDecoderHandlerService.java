package sharespot.services.datadecodermaster.application;

import org.springframework.stereotype.Service;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import sharespot.services.datadecodermaster.domain.SensorTypeId;

import javax.annotation.PostConstruct;

@Service
public class DataDecoderHandlerService {

    private FluxSink<SensorTypeId> dataStream;

    private ConnectableFlux<SensorTypeId> dataPublisher;

    @PostConstruct
    public void init() {
        Flux<SensorTypeId> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Flux<SensorTypeId> getSinglePublisher() {
        return dataPublisher;
    }

    public void publishUpdate(SensorTypeId data) {
        dataStream.next(data);
    }
}
