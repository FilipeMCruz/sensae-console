package sharespot.services.locationtrackingbackend.application;

import org.springframework.stereotype.Component;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import sharespot.services.locationtrackingbackend.domain.model.livedata.RecordEntry;
import sharespot.services.locationtrackingbackend.domain.model.livedata.SensorData;

import javax.annotation.PostConstruct;
import java.util.List;

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

    public Flux<SensorData> getGeneralPublisher() {
        return dataPublisher;
    }

    public Flux<SensorData> getContentFilteredPublisher(String content) {
        return dataPublisher
                .filter(gpsData -> gpsData.device().records()
                        .stream()
                        .map(RecordEntry::content)
                        .anyMatch(e -> e.contains(content)));
    }

    public Flux<SensorData> getSinglePublisher(List<String> ids) {
        return dataPublisher
                .filter(gpsData -> ids.contains(gpsData.device().id().toString()));
    }

    public void publish(SensorData data) {
        dataStream.next(data);
    }
}
