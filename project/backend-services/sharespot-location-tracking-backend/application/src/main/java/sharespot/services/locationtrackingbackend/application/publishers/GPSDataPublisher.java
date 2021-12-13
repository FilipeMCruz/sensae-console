package sharespot.services.locationtrackingbackend.application.publishers;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import sharespot.services.locationtrackingbackend.domain.sensor.gps.RecordEntry;
import sharespot.services.locationtrackingbackend.domain.sensor.gps.SensorData;

import javax.annotation.PostConstruct;

@Component
public class GPSDataPublisher {

    private FluxSink<SensorData> dataStream;
    private ConnectableFlux<SensorData> dataPublisher;

    @PostConstruct
    public void init() {
        Flux<SensorData> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Publisher<SensorData> getGeneralPublisher() {
        return dataPublisher;
    }

    public Publisher<SensorData> getContentFilteredPublisher(String content) {
        return dataPublisher
                .filter(gpsData -> gpsData.record()
                        .stream()
                        .map(RecordEntry::content)
                        .anyMatch(e -> e.contains(content)));
    }

    public Publisher<SensorData> getSinglePublisher(String id) {
        return dataPublisher
                .filter(gpsData -> gpsData.device().has(id));
    }

    public void publish(SensorData data) {
        dataStream.next(data);
    }
}
