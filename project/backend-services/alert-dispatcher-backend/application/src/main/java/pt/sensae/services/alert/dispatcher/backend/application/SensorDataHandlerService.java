package pt.sensae.services.alert.dispatcher.backend.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.model.SensorDataDTO;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.annotation.PostConstruct;

@Service
public class SensorDataHandlerService {

    private FluxSink<SensorDataDTO> dataStream;

    private ConnectableFlux<SensorDataDTO> dataPublisher;

    @PostConstruct
    public void init() {
        Flux<SensorDataDTO> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Flux<SensorDataDTO> getSinglePublisher() {
        return dataPublisher;
    }

    public void publish(SensorDataDTO data) {
        dataStream.next(data);
    }
}
