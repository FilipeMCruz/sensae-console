package sharespot.services.datavalidator.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.routing.MessageConsumed;
import pt.sharespot.iot.core.routing.MessageSupplied;
import pt.sharespot.iot.core.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.routing.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.data.GPSDataDTO;
import pt.sharespot.iot.core.sensor.properties.PropertyName;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class SensorDataHandlerService {

    private FluxSink<MessageSupplied<ProcessedSensorDataDTO>> dataStream;

    private ConnectableFlux<MessageSupplied<ProcessedSensorDataDTO>> dataPublisher;

    private final RoutingKeysProvider provider;

    private final List<BoundingBox> boxes;

    public SensorDataHandlerService(RoutingKeysProvider provider) {
        this.provider = provider;
        this.boxes = new ArrayList<>();
        initBoxes();
    }

    public void initBoxes() {
        this.boxes.add(new BoundingBox("PT", 45, 35, -10, -5));
        this.boxes.add(new BoundingBox("EN", 45, 35, -5, 0));
        this.boxes.add(new BoundingBox("AN", 45, 40, 0, 5));
        this.boxes.add(new BoundingBox("FR", 50, 45, -5, 10));
        this.boxes.add(new BoundingBox("GR", 55, 45, 5, 15));
    }

    @PostConstruct
    public void init() {
        Flux<MessageSupplied<ProcessedSensorDataDTO>> publisher = Flux.create(emitter -> dataStream = emitter);

        dataPublisher = publisher.publish();
        dataPublisher.connect();
    }

    public Flux<MessageSupplied<ProcessedSensorDataDTO>> getSinglePublisher() {
        return dataPublisher;
    }

    public void publish(MessageConsumed<ProcessedSensorDataDTO> message) {
        var legitimacy = DataLegitimacyOptions.UNDETERMINED;

        if (message.data.hasProperty(PropertyName.LATITUDE) && message.data.hasProperty(PropertyName.LONGITUDE)) {
            if (inside(message.data.data.gps, boxes)) {
                legitimacy = DataLegitimacyOptions.CORRECT;
            } else {
                legitimacy = DataLegitimacyOptions.INCORRECT;
            }
        }
        var routingKeys = provider.getBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withLegitimacyType(legitimacy)
                .from(message.routingKeys);
        routingKeys.ifPresent(keys -> dataStream.next(new MessageSupplied<>(keys, message.data)));
    }

    //TODO: consider this https://developers.google.com/maps/documentation/geocoding/requests-reverse-geocoding
    private boolean inside(GPSDataDTO gps, List<BoundingBox> boxes) {
        return boxes.stream().anyMatch(box -> box.isInside(gps.latitude, gps.longitude));
    }
}
