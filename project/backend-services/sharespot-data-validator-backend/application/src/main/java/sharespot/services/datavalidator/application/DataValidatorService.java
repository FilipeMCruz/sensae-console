package sharespot.services.datavalidator.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.routing.keys.RoutingKeys;
import pt.sharespot.iot.core.routing.keys.RoutingKeysBuilderOptions;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.data.GPSDataDTO;
import pt.sharespot.iot.core.sensor.properties.PropertyName;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DataValidatorService {

    private final RoutingKeysProvider provider;

    private final List<BoundingBox> boxes;

    public DataValidatorService(RoutingKeysProvider provider) {
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
        this.boxes.add(new BoundingBox("DE/NW", 65, 55, 5, 15));
        this.boxes.add(new BoundingBox("PL/BL/UR/HUN", 55, 45, 15, 40));
        this.boxes.add(new BoundingBox("IT/SE/GR", 45, 35, 10, 30));
        this.boxes.add(new BoundingBox("UK", 60, 50, -10, 5));
    }

    public Optional<RoutingKeys> decide(ProcessedSensorDataDTO data, RoutingKeys keys) {
        var legitimacy = DataLegitimacyOptions.UNDETERMINED;

        //TODO: check all other properties

        if (data.hasAllProperties(PropertyName.LATITUDE, PropertyName.LONGITUDE)) {
            if (inside(data.data.gps, boxes)) {
                legitimacy = DataLegitimacyOptions.CORRECT;
            } else {
                legitimacy = DataLegitimacyOptions.INCORRECT;
            }
        }

        if (data.hasProperty(PropertyName.ALTITUDE)) {
            if (data.data.gps.altitude > 5000 || data.data.gps.altitude < -50) {
                legitimacy = DataLegitimacyOptions.INCORRECT;
            }
        }

        return provider.getBuilder(RoutingKeysBuilderOptions.SUPPLIER)
                .withUpdated(data)
                .withLegitimacyType(legitimacy)
                .from(keys);
    }

    //TODO: consider this https://developers.google.com/maps/documentation/geocoding/requests-reverse-geocoding
    private boolean inside(GPSDataDTO gps, List<BoundingBox> boxes) {
        return boxes.stream().anyMatch(box -> box.isInside(gps.latitude, gps.longitude));
    }
}
