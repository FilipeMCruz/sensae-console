package sharespot.services.lgt92gpsdataprocessor.application;

import pt.sharespot.iot.core.sensor.properties.PropertyTransformations;

public class DataTransformation {

    private final SensorTypeId id;

    private final PropertyTransformations transform;

    public DataTransformation(SensorTypeId id, PropertyTransformations transform) {
        this.id = id;
        this.transform = transform;
    }

    public boolean is(SensorTypeId other) {
        return other.same(id);
    }

    public PropertyTransformations getTransform() {
        return transform;
    }
}
