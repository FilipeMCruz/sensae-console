package pt.sensae.services.data.validator.backend.application.validators.gps;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.routing.keys.DataLegitimacyOptions;
import pt.sharespot.iot.core.sensor.ProcessedSensorDataDTO;
import pt.sharespot.iot.core.sensor.data.types.GPSDataDTO;
import pt.sharespot.iot.core.sensor.properties.PropertyName;
import pt.sensae.services.data.validator.backend.application.validators.DataValidator;

import java.util.ArrayList;
import java.util.List;

@Service
public class GPSDataValidator implements DataValidator {

    private final List<BoundingBox> boxes;

    public GPSDataValidator() {
        this.boxes = new ArrayList<>();
        initBoxes();
    }

    @Override
    public DataLegitimacyOptions validate(ProcessedSensorDataDTO data) {
        var legitimacy = DataLegitimacyOptions.CORRECT;

        if (data.hasAllProperties(PropertyName.LATITUDE, PropertyName.LONGITUDE)) {
            if (!inside(data.getSensorData().gps, boxes)) {
                legitimacy = DataLegitimacyOptions.INCORRECT;
            }
        }

        if (data.hasProperty(PropertyName.ALTITUDE)) {
            if (data.getSensorData().gps.altitude > 5000 || data.getSensorData().gps.altitude < -50) {
                legitimacy = DataLegitimacyOptions.INCORRECT;
            }
        }
        return legitimacy;
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

    //TODO: consider this https://developers.google.com/maps/documentation/geocoding/requests-reverse-geocoding
    private boolean inside(GPSDataDTO gps, List<BoundingBox> boxes) {
        return boxes.stream().anyMatch(box -> box.isInside(gps.latitude, gps.longitude));
    }
}
