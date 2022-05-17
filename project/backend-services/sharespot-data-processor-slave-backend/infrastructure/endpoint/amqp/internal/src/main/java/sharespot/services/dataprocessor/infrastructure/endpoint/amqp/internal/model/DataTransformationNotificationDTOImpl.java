package sharespot.services.dataprocessor.infrastructure.endpoint.amqp.internal.model;

import sharespot.services.dataprocessor.application.DataTransformationNotificationDTO;

public class DataTransformationNotificationDTOImpl implements DataTransformationNotificationDTO {

    public String sensorType;

    public DataTransformationNotificationTypeDTOImpl type;

    public DataTransformationDTOImpl information;
}
