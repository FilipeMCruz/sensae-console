package pt.sensae.services.data.processor.flow.infrastructure.endpoint.amqp.internal.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import pt.sensae.services.data.processor.flow.application.model.DataTransformationNotificationDTO;

@RegisterForReflection
public class DataTransformationNotificationDTOImpl implements DataTransformationNotificationDTO {

    public String sensorType;

    public DataTransformationNotificationTypeDTOImpl type;

    public DataTransformationDTOImpl information;
}
