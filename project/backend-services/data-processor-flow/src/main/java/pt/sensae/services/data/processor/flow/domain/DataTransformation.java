package pt.sensae.services.data.processor.flow.domain;

import pt.sharespot.iot.core.sensor.model.properties.PropertyTransformations;

public record DataTransformation(SensorTypeId id, PropertyTransformations transform) {

}
