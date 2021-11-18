package pt.sharespot.services.locationtrackingbackend.application.publishers;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import pt.sharespot.services.locationtrackingbackend.application.exceptions.PublishErrorException;
import pt.sharespot.services.locationtrackingbackend.domain.sensor.gps.GPSData;
import reactor.core.publisher.Sinks;

import java.util.UUID;

@Component
public class GPSDataPublisher {

    private final Sinks.Many<GPSData> sink;

    public GPSDataPublisher() {
        this.sink = Sinks
                .many()
                .multicast()
                .onBackpressureBuffer();
    }

    public Publisher<GPSData> getGeneralPublisher() {
        return sink.asFlux();
    }

    public Publisher<GPSData> getSinglePublisher(UUID id) {
        return sink.asFlux()
                .filter(gpsData -> gpsData.deviceId().equals(id));
    }

    public void publish(GPSData data) {
        var result = sink.tryEmitNext(data);

        if (result.isFailure()) {
            throw new PublishErrorException("Failed to publish GPSData, reason: " + result.name());
        }
    }
}
