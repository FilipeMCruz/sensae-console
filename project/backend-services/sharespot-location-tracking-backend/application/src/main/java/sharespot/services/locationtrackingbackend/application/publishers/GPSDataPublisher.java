package sharespot.services.locationtrackingbackend.application.publishers;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import sharespot.services.locationtrackingbackend.application.exceptions.PublishErrorException;
import sharespot.services.locationtrackingbackend.domain.sensor.gps.SensorData;
import reactor.core.publisher.Sinks;

import java.util.UUID;

@Component
public class GPSDataPublisher {

    private final Sinks.Many<SensorData> sink;

    public GPSDataPublisher() {
        this.sink = Sinks
                .many()
                .multicast()
                .onBackpressureBuffer();
    }

    public Publisher<SensorData> getGeneralPublisher() {
        return sink.asFlux();
    }

    public Publisher<SensorData> getSinglePublisher(UUID id) {
        return sink.asFlux()
                .filter(gpsData -> gpsData.deviceId().equals(id));
    }

    public void publish(SensorData data) {
        var result = sink.tryEmitNext(data);

        if (result.isFailure()) {
            throw new PublishErrorException("Failed to publish GPSData, reason: " + result.name());
        }
    }
}
