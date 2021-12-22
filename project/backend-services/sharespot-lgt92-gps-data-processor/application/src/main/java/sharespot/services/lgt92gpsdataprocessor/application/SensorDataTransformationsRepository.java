package sharespot.services.lgt92gpsdataprocessor.application;

import org.springframework.stereotype.Service;
import pt.sharespot.iot.core.sensor.properties.PropertyName;
import pt.sharespot.iot.core.sensor.properties.PropertyTransformation;
import pt.sharespot.iot.core.sensor.properties.PropertyTransformations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SensorDataTransformationsRepository {

    private final List<DataTransformation> transformations = new ArrayList<>();

    public SensorDataTransformationsRepository() {
        init();
    }

    private void init() {
        var lgt92propertyTransformations = PropertyTransformations.of(
                PropertyTransformation.create("decoded.payload.Latitude", PropertyName.LATITUDE),
                PropertyTransformation.create("decoded.payload.Longitude", PropertyName.LONGITUDE),
                PropertyTransformation.create("uuid", PropertyName.DATA_ID),
                PropertyTransformation.create("id", PropertyName.DEVICE_ID),
                PropertyTransformation.create("name", PropertyName.DEVICE_NAME),
                PropertyTransformation.create("reported_at", PropertyName.REPORTED_AT)
        );
        transformations.add(new DataTransformation(SensorTypeId.of("lgt92"), lgt92propertyTransformations));
    }

    public Optional<DataTransformation> getById(SensorTypeId id) {
        return transformations.stream().filter(t -> t.is(id)).findFirst();
    }
}
