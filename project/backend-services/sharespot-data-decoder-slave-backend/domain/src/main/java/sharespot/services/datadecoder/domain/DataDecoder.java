package sharespot.services.datadecoder.domain;

import pt.sharespot.iot.core.sensor.properties.PropertyTransformations;

public class DataDecoder {

    private final SensorTypeId id;

    private final PropertyTransformations transform;

    public DataDecoder(SensorTypeId id, PropertyTransformations transform) {
        this.id = id;
        this.transform = transform;
    }

    public PropertyTransformations getTransform() {
        return transform;
    }
}
