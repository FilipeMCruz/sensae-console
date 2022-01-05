package sharespot.services.locationtrackingbackend.application;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataWithRecordsDTO;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.annotation.PostConstruct;

@Component
public class GPSDataPublisher {

    private FluxSink<ProcessedSensorDataWithRecordsDTO> dataStream;
    private ConnectableFlux<ProcessedSensorDataWithRecordsDTO> dataPublisher;
    private final SensorDataMapper mapper;

    public GPSDataPublisher(SensorDataMapper mapper) {
        this.mapper = mapper;
    }

    @PostConstruct
    public void init() {
        Flux<ProcessedSensorDataWithRecordsDTO> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Publisher<OutSensorData> getGeneralPublisher() {
        return dataPublisher.map(mapper::transform);
    }

    public Publisher<OutSensorData> getContentFilteredPublisher(String content) {
        return dataPublisher
                .filter(gpsData -> gpsData.device.records.entry
                        .stream()
                        .map(e -> e.content)
                        .anyMatch(e -> e.contains(content)))
                .map(mapper::transform);
    }

    public Publisher<OutSensorData> getSinglePublisher(String id) {
        return dataPublisher
                .filter(gpsData -> gpsData.device.id.toString().equals(id))
                .map(mapper::transform);

    }

    public void publish(ProcessedSensorDataWithRecordsDTO data) {
        dataStream.next(data);
    }
}
