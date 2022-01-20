package sharespot.services.locationtrackingbackend.application;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import sharespot.services.locationtrackingbackend.domain.ProcessedSensorDataRepository;
import sharespot.services.locationtrackingbackend.domain.model.livedata.SensorData;

import javax.annotation.PostConstruct;

@Component
public class GPSDataPublisher {

    private FluxSink<ProcessedSensorDataWithRecordsDTO> dataStream;
    private ConnectableFlux<ProcessedSensorDataWithRecordsDTO> dataPublisher;
    private final ProcessedSensorDataRepository repository;

    public GPSDataPublisher(ProcessedSensorDataRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void init() {
        Flux<ProcessedSensorDataWithRecordsDTO> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Flux<ProcessedSensorDataWithRecordsDTO> getGeneralPublisher() {
        return dataPublisher;
    }

    public Flux<ProcessedSensorDataWithRecordsDTO> getContentFilteredPublisher(String content) {
        return dataPublisher
                .filter(gpsData -> gpsData.device.records.entry
                        .stream()
                        .map(e -> e.content)
                        .anyMatch(e -> e.contains(content)));
    }

    public Flux<ProcessedSensorDataWithRecordsDTO> getSinglePublisher(String id) {
        return dataPublisher
                .filter(gpsData -> gpsData.device.id.toString().equals(id) || gpsData.device.name.equals(id));

    }

    public void publish(ProcessedSensorDataWithRecordsDTO data) {
        dataStream.next(data);
        repository.insert(data);
    }
}
