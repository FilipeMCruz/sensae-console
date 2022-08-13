package pt.sensae.services.data.processor.flow.infrastructure.endpoint.amqp.internal.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import pt.sensae.services.data.processor.flow.application.model.SensorTypeIdDTO;

@RegisterForReflection
public class SensorTypeIdDTOImpl implements SensorTypeIdDTO {
    public String sensorType;
}
