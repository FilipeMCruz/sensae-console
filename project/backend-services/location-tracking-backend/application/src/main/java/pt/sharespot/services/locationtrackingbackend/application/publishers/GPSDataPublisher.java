package pt.sharespot.services.locationtrackingbackend.application.publishers;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pt.sharespot.services.locationtrackingbackend.domain.sensor.gps.GPSData;
import reactor.core.publisher.Sinks;

import java.util.UUID;

@Component
public class GPSDataPublisher {

    Logger logger = LoggerFactory.getLogger(GPSDataPublisher.class);

    private final Sinks.Many<GPSData> sink;

    public GPSDataPublisher() {
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
    }

    public Publisher<GPSData> getGeneralPublisher() {
        return sink.asFlux().map(gpsData -> {
            logger.info("new data: " + gpsData.toString());
            return gpsData;
        });
    }

    public Publisher<GPSData> getSinglePublisher(UUID id) {
        return sink.asFlux()
                .filter(gpsData -> gpsData.deviceId().equals(id))
                .map(gpsData -> {
                    logger.info("new data: " + gpsData.toString());
                    return gpsData;
                });
    }

    public void publish(GPSData data) {
        var result = sink.tryEmitNext(data);

        if (result.isFailure()) {
            logger.error("publish GPSData failed");
        }
    }
}
