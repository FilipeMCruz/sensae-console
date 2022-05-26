package sharespot.services.dataprocessor.domain;

import pt.sharespot.iot.core.sensor.model.properties.PropertyTransformations;

public class DataTransformation {

    private final SensorTypeId id;

    private final PropertyTransformations transform;

    public DataTransformation(SensorTypeId id, PropertyTransformations transform) {
        this.id = id;
        this.transform = transform;
    }

    public PropertyTransformations getTransform() {
        return transform;
    }

    public SensorTypeId getId() {
        return id;
    }
}
