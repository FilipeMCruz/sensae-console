package sharespot.services.dataprocessormaster.domain;


import pt.sharespot.iot.core.sensor.model.properties.PropertyTransformations;

public class DataTransformation {

    private final SensorTypeId id;

    private final PropertyTransformations transform;

    public DataTransformation(SensorTypeId id, PropertyTransformations transform) {
        this.id = id;
        this.transform = transform;
    }

    public SensorTypeId getId() {
        return id;
    }

    public PropertyTransformations getTransform() {
        return transform;
    }
}
